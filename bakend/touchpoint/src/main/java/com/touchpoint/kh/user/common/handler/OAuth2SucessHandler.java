package com.touchpoint.kh.user.common.handler;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.touchpoint.kh.user.model.dao.UserMapper;
import com.touchpoint.kh.user.model.vo.CustomOAuth2User;
import com.touchpoint.kh.user.model.vo.User;
import com.touchpoint.kh.user.provider.JwtProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class OAuth2SucessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtProvider jwtProvider;
	private final UserMapper userMapper;
	
	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request, 
			HttpServletResponse response,
			Authentication authentication
			) throws IOException, ServletException {
		
		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
		String userId = oAuth2User.getName();
		String role = "ROLE_USER";
		String token = jwtProvider.create(userId,role);
		
		
		if (userId.startsWith("kakao_")) {
			User user = userMapper.findUserByProviderUserId(userId);	
			boolean isMatched = user.getEmail().equalsIgnoreCase("email@email.com");
			
			if(!isMatched) {
				response.sendRedirect("http://localhost:3000/auth/" + token);	
			} else {
		        response.sendRedirect("http://localhost:3000/socalsignup/" + token);
			}
	    } else {
	    	response.sendRedirect("http://localhost:3000/auth/" + token);		
	    }
	
	}
}

