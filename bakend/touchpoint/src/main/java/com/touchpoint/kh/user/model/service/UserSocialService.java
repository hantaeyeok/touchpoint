package com.touchpoint.kh.user.model.service;

import org.springframework.http.ResponseEntity;

import com.touchpoint.kh.user.model.dto.request.SocialSignUpRequestDto;
import com.touchpoint.kh.user.model.dto.response.SocialSignUpResponseDto;

public interface UserSocialService {

    void saveSocialUser(String provider, String providerUserId, String email, String username);
	ResponseEntity<? super SocialSignUpResponseDto> socialSignUp(SocialSignUpRequestDto dto);

}
