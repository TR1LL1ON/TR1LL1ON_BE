package com.ybe.tr1ll1on.security.oauth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.tr1ll1on.security.dto.TokenInfo;
import com.ybe.tr1ll1on.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) throws IOException, ServletException {
        TokenInfo tokenInfo = jwtTokenProvider.generateTokenInfo(authentication, response);

        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("tokenInfo", tokenInfo);

        String responseBodyJson = objectMapper.writeValueAsString(responseBody);

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.getWriter().write(responseBodyJson);
    }
}