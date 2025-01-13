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
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.touchpoint.kh.user.model.service.oath2.Oath2Service;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean // Spring Security 감지 동시 세션 관리 및 종료 처리 가능
	public HttpSessionEventPublisher httpSessionEventPublisher() {
		return new HttpSessionEventPublisher();
	}
	
	@Bean
	public SessionRegistry sessionRegistry() {
	    return new SessionRegistryImpl();
	}
		

    @Bean
    public Oath2Service oath2Service() {
        return new Oath2Service();
    }
	
    
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,  AuthSuccessHandler authSuccessHandler) throws Exception{
		
		//cors 허용
		http
				.cors(cors -> cors.configurationSource(request -> {
					var corsConfiguration = new org.springframework.web.cors.CorsConfiguration();
		            corsConfiguration.setAllowedOrigins(List.of("http://localhost:3000")); // React 서버 주소
		            corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		            corsConfiguration.setAllowedHeaders(List.of("*")); // 모든 헤더 허용
		            corsConfiguration.setAllowCredentials(true); // 쿠키 허용
		            return corsConfiguration;	
				}));
		
		//권한 설정
		http
				.authorizeHttpRequests((auth) -> auth
						.requestMatchers("/admin/**").hasRole("ADMIN") // 관리자 페이지는 ADMIN 권한만 접근 가능
						.requestMatchers("/admin/**").not().hasRole("USER") // 일반 사용자는 관리자 페이지 접근 불가
						.requestMatchers("/chat/**").not().hasRole("USER")  // 채팅 페이지는 일반 사용자 및 비로그인 사용자는 접근 불가
						.requestMatchers("/chat/**", "/admin/**").authenticated() // 로그인하지 않은 사용자는 채팅과 관리자 페이지 접근 불가
						.anyRequest().permitAll()  // 나머지 경로는 모두 허용
				);
		
		
		// Form 로그인 설정
		http
				.formLogin((auth) -> auth.loginPage("/login")
						.loginProcessingUrl("/loginProc")
						.successHandler(authSuccessHandler)
						.permitAll()
				);
		
		// Session 설정
		http
				.sessionManagement(session -> session
						.maximumSessions(1) // 동시 세션 1개 제한
						.maxSessionsPreventsLogin(false) //기존 세선 무효화
						.expiredUrl("/login?sessionExpired-true") //세션 만료시 url
						.sessionRegistry(sessionRegistry())
				);
		
		
		// OAuth2 로그인 설정
        http.oauth2Login((oauth2) -> oauth2
                .loginPage("/login")                                   // OAuth2 로그인 페이지 경로
                .defaultSuccessUrl("/api/oauth2/callback/success", true)                      // 로그인 성공 후 리다이렉트 경로
                .failureUrl("http://localhost:4000/login?error=true")                       // 로그인 실패 시 리다이렉트 경로
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(oath2Service())    // 사용자 정보를 처리할 서비스 등록
                )
        );
        
        
		
		// CSRF 비활성화
		http
				.csrf((auth) -> auth.disable());
		
		
		return http.build();
	}
	
	
	
    
}