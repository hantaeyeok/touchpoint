package com.touchpoint.kh.config.service;

import java.util.Map;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Oath2Service extends DefaultOAuth2UserService {

	@Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
		
        // 부모 클래스의 loadUser 메서드 호출
        OAuth2User oAuth2User = super.loadUser(userRequest);

        // 사용자 정보 매핑 로직
        Map<String, Object> attributes = oAuth2User.getAttributes();
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        // 사용자 데이터 예시
        String id = (String) response.get("id");
        String email = (String) response.get("email");
        String name = (String) response.get("name");

        System.out.println(id);
        log.info("id", id );
        log.info("email", email );
        log.info("name", name );
        log.info("attributes", attributes );
        log.info("response", response );
        
        
        // 반환 객체 생성
        return new DefaultOAuth2User(
            oAuth2User.getAuthorities(),
            response,
            "id" // 기본 키로 사용할 속성 이름
        );
    }
}
