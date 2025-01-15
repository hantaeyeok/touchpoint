package com.touchpoint.kh.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import com.touchpoint.kh.user.model.service.oath2.AuthLogoutHandler;
import com.touchpoint.kh.user.model.service.oath2.AuthSuccessHandler;
import com.touchpoint.kh.user.model.service.oath2.CustomOAuth2UserService;
import com.touchpoint.kh.user.model.service.oath2.CustomOathHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           AuthSuccessHandler authSuccessHandler,
                                           AuthLogoutHandler authLogoutHandler,
                                           CustomOathHandler customOathHandler,
                                           CustomOAuth2UserService customOAuth2UserService) throws Exception {
        
        // CORS 설정
        http.cors(cors -> cors.configurationSource(request -> {
            var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
            corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000")); // React 서버 주소
            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
            corsConfiguration.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
            corsConfiguration.setAllowCredentials(true); // 쿠키 허용
            return corsConfiguration;
        }));

        // 권한 설정
        http.authorizeHttpRequests(auth -> auth
                .requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 페이지는 ADMIN 권한만 접근 가능
                .requestMatchers("/chat/**").authenticated()   // 채팅 페이지는 인증 필요
                .anyRequest().permitAll()                     // 나머지 경로는 모두 허용
        );

        // Form 로그인 설정
        http.formLogin(formLogin -> formLogin
                .loginPage("/login")
                .successHandler(authSuccessHandler)
                .permitAll()
        );

        // OAuth2 로그인 설정
        http.oauth2Login(oauth2 -> oauth2
                .loginPage("/oauth2/login")
                .successHandler(customOathHandler)
                .failureUrl("http://localhost:3000/oauth2/login?error=true") // 로그인 실패 시 리다이렉트 경로
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(customOAuth2UserService) // 사용자 정보를 처리할 서비스 등록
                )
        );

        // 로그아웃 설정
        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessHandler(authLogoutHandler)
                .invalidateHttpSession(true) // 세션 무효화
                .deleteCookies("JSESSIONID")
                .permitAll()
        );

        // 세션 관리 설정
        http.sessionManagement(session -> session
                .maximumSessions(1) // 동시 세션 1개 제한
                .maxSessionsPreventsLogin(false) // 기존 세션 무효화
                .expiredUrl("/login?sessionExpired=true") // 세션 만료 시 이동 URL
                .sessionRegistry(sessionRegistry())
        );

        // CSRF 비활성화
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }
}
