package com.ybe.tr1ll1on.security.jwt;

import com.ybe.tr1ll1on.global.exception.TrillionExceptionCode;
import com.ybe.tr1ll1on.security.dto.TokenDto;
import com.ybe.tr1ll1on.security.exception.InvalidTokenException;
import com.ybe.tr1ll1on.security.exception.NotTokenException;
import com.ybe.tr1ll1on.security.exception.SecurityExceptionCode;
import com.ybe.tr1ll1on.security.service.CustomUserDetailsService;
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
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.ybe.tr1ll1on.security.constants.JwtConstants.*;

/**
 * JWT token 과 관련된 암호화 및 복호화 및 검증은 해당 클래스에서 수행된다.
 */
@Component
public class JwtTokenProvider {
    private final Key key;
    private final CustomUserDetailsService customUserDetailsService;

    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, CustomUserDetailsService customUserDetailsService) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * 토큰 발급
     */

    // Authentication 정보를 기반으로 Access Token을 생성하고, TokenDto에 담아서 보낸다.
    // 동시에 Refresh Token은 Cookie에 저장한다. ( + Response Header에도 Cookie를 추가 )
    public TokenDto generateTokenDto(Authentication authentication, HttpServletResponse response) {
        long now = (new Date()).getTime();
        String authorities = getAuthorities(authentication);
        String accessToken = buildToken(authentication.getName(), authorities, now + ACCESS_TOKEN_EXPIRE_TIME);
        String refreshToken = buildToken(authentication.getName(), authorities, now + REFRESH_TOKEN_EXPIRE_TIME);

        storeRefreshTokenInCookie(response, refreshToken);

        return TokenDto.builder()
                .grantType(BEARER_TYPE)
                .accessToken(accessToken)
                .accessTokenExpiresIn(now + ACCESS_TOKEN_EXPIRE_TIME)
                .refreshToken(refreshToken).build();
    }

    // 현재 사용자의 권한 정보를 추출하여 쉼표로 구분된 문자열로 반환한다.
    private String getAuthorities(Authentication authentication) {
        // 1. Authentication 객체로부터 권한 목록을 가져온다.
        return authentication.getAuthorities().stream()
                // 2. 각 권한을 해당 권한의 문자열 표현으로 변환한다.
                .map(GrantedAuthority::getAuthority)
                // 3. 모든 권한을 쉼표로 연결하여 하나의 문자열로 결합한다.
                .collect(Collectors.joining(","));
    }

    // 주어진 subject 및 authorities 및 expiration 정보를 이용하여 JWT를 생성하고 반환한다.
    private String buildToken(String subject, String authorities, long expiration) {
        JwtBuilder builder = Jwts.builder()
                .setSubject(subject) // payload "sub": "name"
                .setExpiration(new Date(expiration)) // payload "exp": 151621022 (ex)
                .signWith(key, SignatureAlgorithm.HS512);  // header "alg": "HS512"
        if (authorities != null) {
            builder.claim(AUTHORITIES_KEY, authorities); // payload "auth": "ROLE_USER"
        }
        return builder.compact();
    }

    // Response Cookie를 생성하여 Refresh Token을 저장한다.
    private void storeRefreshTokenInCookie(HttpServletResponse response, String refreshToken) {
        ResponseCookie cookie = ResponseCookie.from(REFRESH_TOKEN, refreshToken)
                .domain("tr1ll1on.site")
                .httpOnly(true)     // 1. JavaScript에서 쿠키에 접근을 막기 위해 httpOnly 설정
                .secure(true)       // 2. HTTPS에서만 쿠키 전송을 허용
                .path("/")      // 3. 쿠키가 전송될 수 있는 경로 설정
                .sameSite("None")    // 4. 동일 사이트와 크로스 사이트에 모두 쿠키 전송이 가능
                .build();
        // Response Header에도 Cookie를 저장한다.
        response.addHeader("Set-Cookie", cookie.toString());
    }

    /**
     * 토큰 검증
     */

    // 주어진 Access Token의 유효성을 검사한다.
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }

    // 주어진 Access Token을 이용하여 Authentication 객체를 생성하여 반환한다.
    public Authentication getAuthentication(String accessToken) {
        // 1. Access Token에서 claims을 파싱한다.
        Claims claims = parseClaims(accessToken);

        // 2. 파싱된 claims에 대한 검증을 수행한다.
        if (claims.get(AUTHORITIES_KEY) == null) {
            throw new InvalidTokenException(SecurityExceptionCode.INVALID_TOKEN);
        }

        // 3. claims에서 authorities를 추출한다.
        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        // 4. claims에서 추출된 authorities를 사용하여 UserDetails를 생성한다.
        // 5. 생성된 Authentication 객체를 반환한다.
        UserDetails principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    // 주어진 Access Token에서 Claims을 파싱한다. 토큰이 만료되었을 경우, 예외에서 클레임을 반환한다.
    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    /**
     * 토큰 재발급
     */

    // Cookie에서 Refresh Token을 가져온다.
    public String getRefreshTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        return Arrays.stream(cookies)
                .filter(cookie -> REFRESH_TOKEN.equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new NotTokenException(SecurityExceptionCode.NOT_TOKEN));
    }

    public Authentication getAuthenticationFromRefreshToken(String refreshToken) {
        if (validateToken(refreshToken)) {
            // 1. Refresh Token에서 claims을 파싱한다.
            Claims claims = parseClaims(refreshToken);
            // 2. claims에서 사용자 아이디를 추출한다.
            String userId = claims.getSubject();
            // 3. 사용자 ID로부터 UserDetails를 가져온다.
            UserDetails userDetails = customUserDetailsService.loadUserById(userId);

            // 4. UserDetails를 이용하여 새로운 Authentication 객체를 생성한다.
            return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        } else {
            throw new InvalidTokenException(SecurityExceptionCode.INVALID_TOKEN);
        }
    }

    // Authentication 정보를 기반으로 Access Token을 새롭게 생성해서 보낸다.
    public String generateAccessToken(Authentication authentication) {
        String authorities = getAuthorities(authentication);
        long now = (new Date()).getTime();

        return buildToken(authentication.getName(), authorities, now + ACCESS_TOKEN_EXPIRE_TIME);
    }
}
