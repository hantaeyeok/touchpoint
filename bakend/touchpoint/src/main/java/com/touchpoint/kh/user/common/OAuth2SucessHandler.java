package com.touchpoint.kh.user.common;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.touchpoint.kh.user.model.vo.CustomOAuth2User;
import com.touchpoint.kh.user.provider.JwtProvider;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class OAuth2SucessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final JwtProvider jwtProvider;
	
	@Override
	public void onAuthenticationSuccess(
			HttpServletRequest request, 
			HttpServletResponse response,
			
			Authentication authentication
			) throws IOException, ServletException {
		
		CustomOAuth2User oAuth2User = (CustomOAuth2User)authentication.getPrincipal();
		String userId = oAuth2User.getName();
		String token = jwtProvider.create(userId);
		
		response.sendRedirect("http://localhost:3000/auth/oauth-response/"+token+"/3600");
		
	}
	
}
/*
 * // Kakao 사용자 여부 확인
    if (userId.startsWith("kakao_")) {
        // 추가 정보 입력 페이지로 리다이렉트
        response.sendRedirect("http://localhost:3000/auth/kakao-additional-info/" + token);
    } else {
        // 일반적인 경우
        response.sendRedirect("http://localhost:3000/auth/oauth-response/" + token + "/3600");
    }
 * */
