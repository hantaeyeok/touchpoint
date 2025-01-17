package com.touchpoint.kh.user.model.service;

import java.util.Map;

import org.springframework.security.oauth2.core.user.OAuth2User;

public interface SocialLoginService {

	Map<String, Object> socialLogin(String provider, OAuth2User oAuth2User);
}
