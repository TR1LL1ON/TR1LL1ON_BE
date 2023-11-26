package com.ybe.tr1ll1on.domain.user.service;

import com.ybe.tr1ll1on.domain.cart.model.Cart;
import com.ybe.tr1ll1on.domain.cart.repository.CartRepository;
import com.ybe.tr1ll1on.domain.user.dto.request.LoginRequest;
import com.ybe.tr1ll1on.domain.user.dto.request.SignUpRequest;
import com.ybe.tr1ll1on.domain.user.dto.response.LoginResponse;
import com.ybe.tr1ll1on.domain.user.dto.response.SignUpResponse;
import com.ybe.tr1ll1on.domain.user.exception.EmailAlreadyExistsException;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.global.exception.TrillionExceptionCode;
import com.ybe.tr1ll1on.security.dto.TokenDto;
import com.ybe.tr1ll1on.security.exception.UserNotFoundException;
import com.ybe.tr1ll1on.security.jwt.JwtTokenProvider;
import com.ybe.tr1ll1on.security.util.SecurityUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new EmailAlreadyExistsException(TrillionExceptionCode.CONFLICT);
        }

        User user = signUpRequest.toEntity(passwordEncoder);
        userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);

        return SignUpResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    @Transactional(readOnly = true)
    public ResponseEntity<LoginResponse> login(LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticate(loginRequest);
        TokenDto tokenDTO = jwtTokenProvider.generateTokenDto(authentication, response);
        HttpHeaders responseHeaders = createAuthorizationHeader(tokenDTO.getAccessToken());

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException(TrillionExceptionCode.USER_NOT_FOUND));

        return new ResponseEntity<>(LoginResponse.builder()
                .email(user.getEmail())
                .id(user.getId())
                .name(user.getName())
                .build(), responseHeaders, HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
        System.out.println(SecurityUtil.getCurrentUserId());
        deleteCookie(response);
        return new ResponseEntity<>("로그아웃에 성공하였습니다.", HttpStatus.OK);
    }

    public ResponseEntity<String> refreshAccessToken(HttpServletRequest request) {
        String refreshToken = jwtTokenProvider.getRefreshTokenFromRequest(request);
        Authentication authentication = jwtTokenProvider.getAuthenticationFromRefreshToken(refreshToken);
        String newAccessToken = jwtTokenProvider.generateAccessToken(authentication);

        HttpHeaders responseHeaders = createAuthorizationHeader(newAccessToken);

        return new ResponseEntity<>("토큰 재발급 성공", responseHeaders, HttpStatus.OK);
    }

    private Authentication authenticate(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken token = loginRequest.toAuthentication();

        return authenticationManagerBuilder.getObject().authenticate(token);
    }

    private HttpHeaders createAuthorizationHeader(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        return headers;
    }

    private void deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);

        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        response.addCookie(cookie);
    }
}