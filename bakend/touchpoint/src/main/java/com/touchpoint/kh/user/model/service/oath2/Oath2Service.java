package com.touchpoint.kh.user.model.service.oath2;

import java.util.Map;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Oath2Service extends DefaultOAuth2UserService {

	//private final UserRepository userRepository;
	
	@Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) {
		
		 // 부모 클래스의 loadUser 메서드를 호출하여 사용자 정보를 가져옴
	    OAuth2User oAuth2User = super.loadUser(userRequest);

	    // 제공자 이름 (google, naver, kakao 등)
	    String provider = userRequest.getClientRegistration().getRegistrationId();

	    // 제공자가 반환한 사용자 정보
	    Map<String, Object> attributes = oAuth2User.getAttributes();

	    // 디버깅용 출력
	    System.out.println("OAuth2 Provider: " + provider);
	    System.out.println("User Attributes: " + attributes);

	    return oAuth2User;
    }
}
