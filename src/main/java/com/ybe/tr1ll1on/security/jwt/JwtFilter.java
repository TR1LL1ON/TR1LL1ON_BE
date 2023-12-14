package com.ybe.tr1ll1on.security.jwt;

import com.ybe.tr1ll1on.security.constants.JwtConstants;
import com.ybe.tr1ll1on.security.exception.SecurityExceptionCode;
import io.jsonwebtoken.JwtException;
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

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // 1. 요청 헤더에서 토큰을 꺼낸다.
        String accessToken = extractToken(request);

        // 2. 토큰이 존재하지 않는 경우 다음 필터로 넘어간다.
        if (accessToken == null) {
            request.setAttribute("exception", SecurityExceptionCode.NOT_TOKEN);
            filterChain.doFilter(request, response);
            return;
        }

        // 3. 토큰이 유효하지 않은 경우 다음 필터로 넘어간다.
        // 4. 정상 토큰이면 해당 토큰으로 Authentication을 가져와서 SecurityContext 에 저장한다.
        try {

            Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

        } catch (JwtException e) {

            request.setAttribute("exception", SecurityExceptionCode.INVALID_TOKEN);
            filterChain.doFilter(request, response);
            return;

        } catch (UsernameNotFoundException e) {

            request.setAttribute("exception", SecurityExceptionCode.USER_NOT_FOUND);
            filterChain.doFilter(request, response);
            return;

        }
        // 5. 다음 필터로 넘어간다.
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JwtConstants.AUTHORIZATION_HEADER);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtConstants.BEARER_PREFIX)) {
            return bearerToken.substring(JwtConstants.BEARER_PREFIX.length());
        }

        return null;
    }
}