package com.ybe.tr1ll1on.domain.user.service;

import static com.ybe.tr1ll1on.domain.user.exception.InValidUserExceptionCode.EMAIL_ALREADY_EXISTS;

import com.ybe.tr1ll1on.domain.cart.model.Cart;
import com.ybe.tr1ll1on.domain.cart.repository.CartRepository;
import com.ybe.tr1ll1on.domain.user.dto.request.LoginRequest;
import com.ybe.tr1ll1on.domain.user.dto.request.SignUpRequest;
import com.ybe.tr1ll1on.domain.user.dto.response.LoginResponse;
import com.ybe.tr1ll1on.domain.user.dto.response.SignUpResponse;
import com.ybe.tr1ll1on.domain.user.exception.InValidUserException;
import com.ybe.tr1ll1on.domain.user.exception.InValidUserExceptionCode;
import com.ybe.tr1ll1on.domain.user.model.User;
import com.ybe.tr1ll1on.domain.user.repository.UserRepository;
import com.ybe.tr1ll1on.global.constants.AuthConstants;
import com.ybe.tr1ll1on.security.dto.TokenInfo;
import com.ybe.tr1ll1on.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class AuthService {

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new InValidUserException(EMAIL_ALREADY_EXISTS);
        }

        User user = signUpRequest.toEntity(passwordEncoder);
        userRepository.save(user);

        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);

        user.setCart(cart);

        return SignUpResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    @Transactional
    public LoginResponse login(LoginRequest loginRequest, HttpServletResponse response) {
        Authentication authentication = authenticateUser(loginRequest);
        TokenInfo tokenInfo = jwtTokenProvider.generateTokenInfo(authentication, response);

        Long userId = Long.valueOf(authentication.getName());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InValidUserException(InValidUserExceptionCode.USER_NOT_FOUND));

        return LoginResponse.builder()
                .userDetails(
                        LoginResponse.UserDetailsResponse.builder()
                                .userId(userId)
                                .userEmail(user.getEmail())
                                .userName(user.getName())
                                .build()
                )
                .tokenInfo(tokenInfo)
                .build();
    }

    @Transactional
    public String logout(HttpServletResponse response) {
        deleteCookie(response);
        return AuthConstants.LOGOUT_SUCCESS_MESSAGE;
    }

    @Transactional
    public TokenInfo refreshAccessToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = jwtTokenProvider.getRefreshTokenFromRequest(request);
        Authentication authentication = jwtTokenProvider.getAuthenticationFromRefreshToken(refreshToken);

        return jwtTokenProvider.generateTokenInfo(authentication, response);
    }

    private Authentication authenticateUser(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = loginRequest.toAuthentication();

        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }


    private void deleteCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", null);

        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setSecure(true);

        response.addCookie(cookie);
    }
}