package com.ybe.tr1ll1on.security.jwt;

import com.ybe.tr1ll1on.security.constants.JwtConstants;
import com.ybe.tr1ll1on.security.dto.TokenInfo;
import com.ybe.tr1ll1on.security.exception.SecurityException;
import com.ybe.tr1ll1on.security.exception.SecurityExceptionCode;
import com.ybe.tr1ll1on.security.service.PrincipalDetailsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.ybe.tr1ll1on.security.constants.JwtConstants.*;

@Component
public class JwtTokenProvider {
    private final Key key;
    private final PrincipalDetailsService customUserDetailsService;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secretKey,
            PrincipalDetailsService customUserDetailsService
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.customUserDetailsService = customUserDetailsService;
    }

    /* 토큰 발급 */

    public TokenInfo generateTokenInfo(Authentication authentication, HttpServletResponse response) {
        // 1. 현재 시간 및 사용자 권한 가져오기
        long currentTimeMillis = System.currentTimeMillis();
        String authorities = getAuthorities(authentication);

        // 2. 액세스 토큰 및 리프레시 토큰 생성
        String accessToken = generateToken(authentication.getName(), authorities, currentTimeMillis + ACCESS_TOKEN_EXPIRE_TIME);
        String refreshToken = generateToken(authentication.getName(), authorities, currentTimeMillis + REFRESH_TOKEN_EXPIRE_TIME);

        // 3. 엑세스 토큰을 쿠키에 저장
        storeRefreshTokenInCookie(response, refreshToken);

        // 4. 토큰 정보를 담은 객체 생성
        return TokenInfo.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(currentTimeMillis + ACCESS_TOKEN_EXPIRE_TIME)
                .refreshToken(refreshToken)
                .build();
    }

    private String getAuthorities(Authentication authentication) {
        // 1. Authentication 객체로부터 권한 목록을 가져온다.
        return authentication.getAuthorities().stream()
                // 2. 각 권한을 해당 권한의 문자열 표현으로 변환한다.
                .map(GrantedAuthority::getAuthority)
                // 3. 모든 권한을 쉼표로 연결하여 하나의 문자열로 결합한다.
                .collect(Collectors.joining(","));
    }

    private String generateToken(String subject, String authorities, long expiration) {

        JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(subject) // payload "sub": "ID"
                .claim(AUTHORITIES_KEY, authorities) // payload "auth": "ROLE_USER"
                .setExpiration(new Date(expiration)) // payfload "exp": 151621022 (ex)
                .signWith(key, SignatureAlgorithm.HS512);  // header "alg": "HS512"

        return jwtBuilder.compact();
    }

    private void storeRefreshTokenInCookie(HttpServletResponse response, String refreshToken) {

        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .httpOnly(false)     // 1. JavaScript에서 쿠키에 접근을 막기 위해 httpOnly 설정
                .secure(true)       // 2. HTTPS에서만 쿠키 전송을 허용
                .path("/")      // 3. 쿠키가 전송될 수 있는 경로 설정
                .sameSite("None")    // 4. 동일 사이트와 크로스 사이트에 모두 쿠키 전송이 가능
                .build();

        response.addHeader("Set-Cookie", cookie.toString());
    }

    /* 토큰 검증 */

    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    public Authentication getAuthentication(String accessToken) {
        // 1. Access Token에서 claims을 파싱한다.
        Claims claims = parseClaims(accessToken);

        // 2. 파싱된 claims에 대한 검증을 수행한다.
        if (claims.get(JwtConstants.AUTHORITIES_KEY) == null) {
            throw new SecurityException(SecurityExceptionCode.INVALID_TOKEN);
        }

        // 3. claims에서 authorities를 추출한다.
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(JwtConstants.AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // 4. claims에서 사용자 아이디를 추출한다,
        Long userId = Long.parseLong(claims.getSubject());

        // 5. claims에서 추출된 사용자 아이디를 사용하여 UserDetails를 생성한다.
        // 6. 생성된 Authentication 객체를 반환한다.
        UserDetails userDetails = customUserDetailsService.loadUserById(userId);

        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    private Claims parseClaims(String accessToken) {

        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(accessToken)
                .getBody();
    }

    /* 토큰 재발급 */

    public String getRefreshTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        return Arrays.stream(cookies)
                .filter(cookie -> REFRESH_TOKEN.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new SecurityException(SecurityExceptionCode.NOT_TOKEN));
    }

    public Authentication getAuthenticationFromRefreshToken(String refreshToken) {
        if (validateToken(refreshToken)) {
            // 1. Refresh Token에서 claims을 파싱한다.
            Claims claims = parseClaims(refreshToken);
            // 2. claims에서 사용자 아이디를 추출한다.
            Long userId = Long.parseLong(claims.getSubject());
            // 3. 사용자 ID로부터 UserDetails를 가져온다.
            UserDetails userDetails = customUserDetailsService.loadUserById(userId);

            // 4. UserDetails를 이용하여 새로운 Authentication 객체를 생성한다.
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } else {
            throw new SecurityException(SecurityExceptionCode.INVALID_TOKEN);
        }
    }

    public String generateAccessToken(Authentication authentication) {
        String authorities = getAuthorities(authentication);
        long now = (new Date()).getTime();

        return generateToken(authentication.getName(), authorities, now + ACCESS_TOKEN_EXPIRE_TIME);
    }
}