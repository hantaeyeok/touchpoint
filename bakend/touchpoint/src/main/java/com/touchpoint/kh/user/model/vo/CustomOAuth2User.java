package com.touchpoint.kh.user.model.vo;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class CustomOAuth2User implements OAuth2User{

	private String userId; //name id
	private String provider; // OAuth2 제공자
    private Map<String, Object> attributes; // 사용자 속성
	
	
	@Override
	public Map<String, Object> getAttributes() {
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getName() {
		
		return this.userId;
	}

	
}
