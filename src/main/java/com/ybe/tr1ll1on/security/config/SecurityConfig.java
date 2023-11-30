package com.ybe.tr1ll1on.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ybe.tr1ll1on.security.jwt.*;
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

import static com.ybe.tr1ll1on.security.constants.JwtConstants.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtTokenProvider jwtTokenProvider;
    private final ObjectMapper objectMapper;
    private final JwtAuthenticationSuccessHandler authenticationSuccessHandler;

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

        // 상품 조회(전체, 개별), 숙소 전체 리뷰 조회, 회원 가입은 로그인 없이 사용 가능하다.
        http.authorizeHttpRequests(request -> request
                .requestMatchers("/").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/products/**").permitAll()
                .requestMatchers("/reviews/{accommodationId}").permitAll()                   
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v1/api-docs/**").permitAll()
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

        // 클라이언트에서 preflight 요청 결과를 저장할 기간을 지정.
        // 3600초 동안 preflight 요청을 캐시하는 설정으로, 첫 요청 이후 60초 동안은 OPTIONS 메소드를 사용하는 예비 요청을 보내지 않음.
        configuration.setMaxAge(MAX_AGE_TIME);

        // 요청을 허용하는 헤더.
        configuration.addAllowedOrigin("https://www.tr1ll1on.site");
        configuration.addAllowedOrigin("http://localhost:5173");
        configuration.addAllowedOrigin("https://tr1ll1on.vercel.app/");

        // 요청을 허용하는 메서드.
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));

        // 요청을 허용하는 헤더.
        configuration.addAllowedHeader("*");

        // Authorization Header 를 브라우저에 노출 시켜서, 클라이언트에서 JavaScript 로 읽을 수 있도록 허용.
        // 인증 정보를 담고 있어 보안에 취약할 수 있기 때문에, Access Token 의 경우 유효 기간을 짧게 설정.
        configuration.addExposedHeader(AUTHORIZATION_HEADER);

        // 클라이언트 요청이 Cookie 및 Authorization Header Token 통해, 자격 증명을 해야 하는 경우 true 설정.
        // 자바스크립트 요청에서 withCredentials : true 설정 시, 해당 요청에 대한 응답을 해줄 수 있는지 나타냄.
        configuration.setAllowCredentials(true);

        // 모든 경로에 대해 위에 정의한 CORS 구성을 등록.
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}
