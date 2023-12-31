package com.ybe.tr1ll1on.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.security.exception.SecurityExceptionCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ExceptionCode errorCode = SecurityExceptionCode.ACCESS_DENIED;

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("status", errorCode.getStatus());
        responseBody.put("code", errorCode.getCode());
        responseBody.put("message", errorCode.getMsg());

        String responseBodyJson = objectMapper.writeValueAsString(responseBody);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorCode.getStatus().value());
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(responseBodyJson);
    }
}
