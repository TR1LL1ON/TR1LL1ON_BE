package com.ybe.tr1ll1on.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionExceptionCode;
import com.ybe.tr1ll1on.security.exception.SecurityExceptionCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpStatus 401 Unauthorized
 * 인증되지 않은 사용자가 인증이 필요한 엔드포인트에 접근하려고 할 때 발생한 예외를 잡아서 JSON 형태의 API 스펙으로 응답하도록 한다.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationEntryPoint(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ExceptionCode exception = (ExceptionCode) request.getAttribute("exception");

        if (exception != null && exception.equals(SecurityExceptionCode.NOT_TOKEN)) {
            exceptionHandler(response, SecurityExceptionCode.NOT_TOKEN);
            return;
        }

        if (exception != null && exception.equals(SecurityExceptionCode.INVALID_TOKEN)) {
            exceptionHandler(response, SecurityExceptionCode.INVALID_TOKEN);
            return;
        }

        if (exception != null && exception.equals(SecurityExceptionCode.USER_NOT_FOUND)) {
            exceptionHandler(response, SecurityExceptionCode.USER_NOT_FOUND);
        }
    }

    public void exceptionHandler(HttpServletResponse response, SecurityExceptionCode errorCode) throws IOException {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", errorCode.getStatus());
        responseBody.put("code", errorCode.getCode());
        responseBody.put("message", errorCode.getMsg());

        String responseBodyJson = objectMapper.writeValueAsString(responseBody);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(401);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(responseBodyJson);
    }
}