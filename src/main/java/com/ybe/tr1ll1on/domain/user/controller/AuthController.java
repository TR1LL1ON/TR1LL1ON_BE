package com.ybe.tr1ll1on.domain.user.controller;

import com.ybe.tr1ll1on.domain.user.dto.request.LoginRequest;
import com.ybe.tr1ll1on.domain.user.dto.request.SignUpRequest;
import com.ybe.tr1ll1on.domain.user.dto.response.LoginResponse;
import com.ybe.tr1ll1on.domain.user.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest, HttpServletResponse response) {
        ResponseEntity<LoginResponse> loginResponse = authService.login(loginRequest, response);
        HttpHeaders headers = loginResponse.getHeaders();
        return ResponseEntity.status(loginResponse.getStatusCode()).headers(headers).body(loginResponse.getBody());
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity<String> logoutResponse = authService.logout(request, response);
        HttpHeaders headers = logoutResponse.getHeaders();
        return ResponseEntity.status(logoutResponse.getStatusCode()).headers(headers).body(logoutResponse.getBody());
    }

    @PostMapping("/refreshAccessToken")
    public ResponseEntity<?> refreshAccessToken(HttpServletRequest request) {
        ResponseEntity<String> result = authService.refreshAccessToken(request);
        return new ResponseEntity<>(result.getBody(), result.getHeaders(), result.getStatusCode());
    }
}