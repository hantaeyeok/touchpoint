package com.touchpoint.kh.user.model.service;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.touchpoint.kh.user.model.vo.CustomOAuth2User;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class OAth2UserServiceImpl extends DefaultOAuth2UserService{

	private final UserSocialService userSocialService;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException{
		
		OAuth2User oAuth2User = super.loadUser(request);
	    
		String provider = request.getClientRegistration().getRegistrationId(); // kakao, naver
        //String providerClientName = request.getClientRegistration().getClientName(); // Kakao, Naver
		
		try {
			System.out.println(new ObjectMapper().writeValueAsString(oAuth2User.getAttributes()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String providerUserId = "example";
		String email = "email@email.com";
        String name = "example";
		
        if ("kakao".equalsIgnoreCase(provider)) {
        	providerUserId = "kakao_"+ oAuth2User.getAttributes().get("id");	
        }
        
        if ("naver".equalsIgnoreCase(provider)) {
        	Map<String, String> responseMap = (Map<String, String>)oAuth2User.getAttributes().get("response");
        	providerUserId = "naver_"+responseMap.get("id").substring(0,14);
			email = responseMap.get("email").replace("jr.", "");
			name = responseMap.get("name");
        }

        
        userSocialService.saveSocialUser(provider, providerUserId, email, name);		
		
	    return new CustomOAuth2User(providerUserId, provider, oAuth2User.getAttributes());
	}
}

















