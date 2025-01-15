package com.touchpoint.kh.user.model.service.oath2;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CustomOathHandler implements AuthenticationSuccessHandler {

	public CustomOathHandler() {
        log.info("CustomOathHandler bean instantiated");
    }
	
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
	                                    Authentication authentication) throws IOException, ServletException {
	    
		
		
		log.info("Authentication object: {}", authentication);
	    if (authentication == null) {
	        log.warn("Authentication object is null.");
	        response.sendRedirect("http://localhost:3000/login?error=true");
	        return;
	    }

	    OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
	    String provider = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId(); // 수정된 부분
	    log.info("OAuth2 Provider: {}", provider);
	    log.info("OAuth2User Attributes: {}", oAuth2User.getAttributes());

	    if (provider == null) {
	        response.sendRedirect("http://localhost:3000/login?error=true");
	        return;
	    }

	    String redirectUrl = "/oauth2/callback/" + provider + "/success";
	    log.info("Redirecting to: {}", redirectUrl);
	    response.sendRedirect(redirectUrl);
	}

	
    
    
}
