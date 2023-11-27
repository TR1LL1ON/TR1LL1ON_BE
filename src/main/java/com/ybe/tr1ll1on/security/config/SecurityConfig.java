package com.ybe.tr1ll1on.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.tr1ll1on.security.jwt.JwtAuthenticationProvider;
import com.ybe.tr1ll1on.security.jwt.JwtAccessDeniedHandler;
import com.ybe.tr1ll1on.security.jwt.JwtAuthenticationEntryPoint;
import com.ybe.tr1ll1on.security.jwt.JwtTokenProvider;
import com.ybe.tr1ll1on.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration, CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        ProviderManager authenticationManager = (ProviderManager) authenticationConfiguration.getAuthenticationManager();
        authenticationManager.getProviders().add(new JwtAuthenticationProvider(customUserDetailsService, passwordEncoder));
        return authenticationManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors().configurationSource(corsConfigurationSource()); // CORS 구성을 위한 설정 소스 지정

        http
                // 1. CSRF 토큰 비활성화 - RESTful API는 상태를 저장하지 않고, 클라이언트가 토큰과 같은 방식으로 인증을 처리한다.
                .csrf().disable()
                // 2. Spring Security의 기본 로그인 폼을 비활성화
                .formLogin().disable()
                // 3. HTTP 기본 인증을 비활성화 - RESTful API는 일반적으로 HTTP 기본 인증이 아닌 토큰 기반의 인증을 사용한다.
                .httpBasic().disable();
        http
                // Exception Handling 시 사용자 정의 예외 처리 핸들러 클래스가 추가된다.
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)  // 1. 접근 거부 시 사용할 핸들러를 설정
                .authenticationEntryPoint(jwtAuthenticationEntryPoint); // 2. 인증이 필요한 자원에 대한 요청에 대한 인증이 되지 않은 경우 호출되는 핸들러 설정

        http
                // X-Frame-Options 헤더 설정
                .headers()
                .frameOptions()
                .sameOrigin();

        http
                // 1. 시큐리티는 기본적으로 세션을 사용한다.
                // 2. 현재 세션을 사용하지 않기 때문에, 세션을 생성하지 않도록 설정 (STATELESS 모드)
                .sessionManagement()
                .sessionCreationPolicy(STATELESS);

        // 상품 조회(전체, 개별), 회원 가입은 로그인 없이 사용 가능하다.
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/products/**").permitAll()
                .anyRequest().authenticated()
        );

        http
                .apply(new JwtSecurityConfig(jwtTokenProvider, objectMapper));

        http
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/products")
                .clearAuthentication(true);

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(
                List.of("http://localhost:3000"));
        configuration.setAllowedMethods(
                Arrays.asList("HEAD", "POST", "GET", "DELETE", "PUT", "OPTIONS", "PATCH"));
        configuration.addAllowedHeader(("*"));
        configuration.addExposedHeader("Authorization");
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}