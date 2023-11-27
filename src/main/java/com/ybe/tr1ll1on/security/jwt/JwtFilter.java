package com.ybe.tr1ll1on.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.tr1ll1on.global.exception.TrillionExceptionCode;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.ybe.tr1ll1on.security.constants.JwtConstants.*;

/**
 * 실제 필터링 로직은 doFilterInternal 에 들어간다.
 * JWT token 의 Authentication 정보를 SecurityContextHolder 의 SecurityContext 에 저장하는 역할을 수행한다.
 */
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    // 1. 인증(Authentication)이 필요한 요청(Request)이 온다.
    // 2. 요청 헤더(Request Header)에서 엑세스 토큰(Access Token)을 추출하고 정상 토큰인지 검사한다.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 1. 요청 헤더에서 토큰을 꺼낸다.
        String accessToken = resolveToken(request);

        // 2. 토큰이 존재하지 않는 경우 다음 필터로 넘어간다.
        if (accessToken == null) {
            request.setAttribute("exception", TrillionExceptionCode.NOT_TOKEN);
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 토큰이 유효하지 않은 경우 다음 필터로 넘어간다.
        if (!jwtTokenProvider.validateToken(accessToken)) {
            request.setAttribute("exception", TrillionExceptionCode.INVALID_TOKEN);
            filterChain.doFilter(request, response);
            return;
        }

        // 4. 정상 토큰이면 해당 토큰으로 Authentication을 가져와서 SecurityContext 에 저장한다.
        Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
        try {
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (UsernameNotFoundException e) {
            request.setAttribute("exception", TrillionExceptionCode.USER_NOT_FOUND);
        }
        // 5. 다음 필터로 넘어간다.
        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);

        }
        return null;
    }
}
