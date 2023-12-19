package com.ybe.tr1ll1on.security.oauth;

import com.ybe.tr1ll1on.security.dto.TokenInfo;
import com.ybe.tr1ll1on.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) throws IOException, ServletException {
        TokenInfo tokenInfo = jwtTokenProvider.generateTokenInfo(authentication, response);

        String targetUrl = UriComponentsBuilder.fromUriString("http://localhost:5173/oauth2/redirect")
                .queryParam("accessToken", tokenInfo.getAccessToken())
                .build()
                .encode(StandardCharsets.UTF_8)
                .toUriString();

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }
}