package com.touchpoint.kh.user.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/index", "/product", "/error").permitAll() // 특정 경로 허용
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // 정적 리소스 허용
                .anyRequest().authenticated() // 그 외 요청은 인증 필요
            );
        return http.build();
    }
}