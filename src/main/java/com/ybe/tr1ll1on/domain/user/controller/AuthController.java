package com.ybe.tr1ll1on.domain.user.controller;

import com.ybe.tr1ll1on.domain.user.dto.request.LoginRequest;
import com.ybe.tr1ll1on.domain.user.dto.request.SignUpRequest;
import com.ybe.tr1ll1on.domain.user.dto.response.LoginResponse;
import com.ybe.tr1ll1on.domain.user.dto.response.SignUpResponse;
import com.ybe.tr1ll1on.domain.user.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "로그인 API", description = "로그인 관련 API 모음입니다.")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Operation(summary = "회원가입 API", description = "회원가입 API 입니다.")
    @ApiResponse(responseCode = "201", description = "가입 성공시",
            content = @Content(schema = @Schema(implementation = SignUpResponse.class)))
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(
            @Valid @RequestBody SignUpRequest signUpRequest
    ) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "로그인 API 입니다.")
    @ApiResponse(responseCode = "201", description = "로그인 성공시",
            content = @Content(schema = @Schema(implementation = LoginResponse.class)))
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest loginRequest, HttpServletResponse response
    ) {
        return ResponseEntity.ok(authService.login(loginRequest, response));
    }

    @PostMapping("/logout")
    @Operation(summary = "로그아웃 API", description = "로그아웃 API 입니다.")
    @ApiResponse(responseCode = "201", description = "로그아웃 성공시")
    @SecurityRequirement(name = "jwt")
    public ResponseEntity<String> logout(
            HttpServletResponse response
    ) {
        return ResponseEntity.ok(authService.logout(response));
    }


    @PostMapping("/refreshAccessToken")
    @Operation(summary = "리프레쉬 토큰 API", description = "리프레쉬 API 입니다.")
    @ApiResponse(responseCode = "201", description = "리프레쉬 성공시")
    @SecurityRequirement(name = "jwt")
    public ResponseEntity<?> refreshAccessToken(
            HttpServletRequest request
    ) {
        ResponseEntity<String> result = authService.refreshAccessToken(request);
        return new ResponseEntity<>(result.getBody(), result.getHeaders(), result.getStatusCode());
    }
}