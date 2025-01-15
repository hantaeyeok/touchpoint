package com.touchpoint.kh.user.model.service.oath2;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("Starting CustomOAuth2UserService for provider: {}", userRequest.getClientRegistration().getRegistrationId());

        // Spring Security 기본 OAuth2UserService 호출
        OAuth2User oAuth2User = new DefaultOAuth2UserService().loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // 네이버 응답 데이터 확인
        log.info("OAuth2 User Attributes: {}", attributes);

        // 네이버 응답에서 'response' 필드 확인
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");
        if (response == null) {
            log.error("No 'response' field in Naver attributes");
            throw new OAuth2AuthenticationException("Invalid response from Naver");
        }

        // 사용자 이메일 확인
        String email = (String) response.get("email");
        if (email == null) {
            log.error("No email found in Naver response: {}", response);
            throw new OAuth2AuthenticationException("Email is required but not provided by Naver");
        }

        log.info("Successfully loaded user with email: {}", email);

        // 사용자 정보 반환
        return new DefaultOAuth2User(
            Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")),
            response,
            "email"
        );
    }
}

