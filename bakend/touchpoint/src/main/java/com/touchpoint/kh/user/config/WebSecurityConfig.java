package com.touchpoint.kh.user.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.touchpoint.kh.user.common.OAuth2SucessHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Configurable
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final DefaultOAuth2UserService oAuth2UserService;
	private final OAuth2SucessHandler oAuth2SucessHandler;
	
	@Bean
	protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {
		
		httpSecurity
		
		    .cors(cors -> cors
		        .configurationSource(corsConfigurationSource())
		    )
		    
		    .csrf(CsrfConfigurer::disable)
		    
		    .httpBasic(HttpBasicConfigurer::disable)
		    
		    .sessionManagement(sessionManagement -> sessionManagement
			        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			        .maximumSessions(1) // 동시 세션 1개 제한
					.maxSessionsPreventsLogin(false) // 기존 세션 무효화
					.expiredUrl("/login?sessionExpired=true") // 세션 만료 시 이동 URL
				    .sessionRegistry(sessionRegistry())
	    			)
		    
			.authorizeHttpRequests(request -> request
				    .requestMatchers("/user/**").hasRole("USER")
				    .requestMatchers("/admin/**").hasRole("ADMIN")
				    .anyRequest().permitAll()
					)
			
			.oauth2Login(oauth2 -> oauth2
					.authorizationEndpoint(endpoint -> endpoint.baseUri("/oauth2/authorization/"))
					.redirectionEndpoint(endpoint -> endpoint.baseUri("/oauth2/callback/*"))
		    		.userInfoEndpoint(endpoint -> endpoint.userService(oAuth2UserService))
		    		.successHandler(oAuth2SucessHandler)
		    	    .failureHandler(new SimpleUrlAuthenticationFailureHandler("/login?error=true")) // 실패 시 리다이렉트 URL 설정
		    		)
			
		    .exceptionHandling(exceptionHandling -> exceptionHandling
		    		.authenticationEntryPoint(new FailedAuthenticationEntryPoint())		
		    		)
		    
		    .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		
			
		return httpSecurity.build();
		}
	
	@Bean
	protected CorsConfigurationSource corsConfigurationSource() {
		
		CorsConfiguration corsConfiguration = new CorsConfiguration();
		corsConfiguration.addAllowedOrigin("*");
		corsConfiguration.addAllowedMethod("*");
		corsConfiguration.addAllowedHeader("*");
		
		UrlBasedCorsConfigurationSource soruse = new UrlBasedCorsConfigurationSource();
		soruse.registerCorsConfiguration("/**", corsConfiguration);
		
		return soruse;
	}
	
	@Bean
    public SessionRegistryImpl sessionRegistry() {
        return new SessionRegistryImpl();
    }

    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }
	
}



class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint{

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {

		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		//{"code": "NP", "message": "No permission."}
		response.getWriter().write("{\"code\": \"NP\", \"message\": \"No permission.\"}");
	}
	
	
}






