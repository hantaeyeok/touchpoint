package com.touchpoint.kh.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE) // 필터 실행 순서를 가장 높은 우선순위로 설정
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

            .csrf().disable() // CSRF 보호 비활성화
            .cors() // CORS 활성화
            .and()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/*", "/product/**").permitAll() // /api/ 경로 허용
                .anyRequest().authenticated() // 나머지 요청은 인증 필요
            );
        return http.build();
    }

    // 글로벌 CORS 설정
  
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // /api/** 경로만 적용
                        .allowedOrigins("http://localhost:3000") // React 서버 주소
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 메서드
                        .allowedHeaders("*") // 모든 헤더 허용
                        .allowCredentials(true); // 쿠키 허용
            }
        };
    }
}