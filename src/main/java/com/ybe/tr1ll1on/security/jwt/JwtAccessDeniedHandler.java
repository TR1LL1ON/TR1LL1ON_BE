package com.ybe.tr1ll1on.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.tr1ll1on.global.exception.ExceptionCode;
import com.ybe.tr1ll1on.global.exception.TrillionExceptionCode;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * HttpStatus 403 Forbidden
 *  인증 되었지만 요청에 대한 권한을 가지고 있지 않은 엔드포인트에 접근하려고 할 때, 발생한 예외를 잡아서 JSON 형태의 API 스펙으로 응답하도록 한다.
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ExceptionCode errorCode = TrillionExceptionCode.ACCESS_DENIED;

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
