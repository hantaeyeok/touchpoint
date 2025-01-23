package com.touchpoint.kh.user.model.service;


import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.touchpoint.kh.user.model.dao.UserMapper;
import com.touchpoint.kh.user.model.dao.UserRepository;
import com.touchpoint.kh.user.model.dao.UserSocialRepository;
import com.touchpoint.kh.user.model.dto.request.SignUpRequestDto;
import com.touchpoint.kh.user.model.dto.request.SocialSignUpRequestDto;
import com.touchpoint.kh.user.model.dto.response.ResponseDto;
import com.touchpoint.kh.user.model.dto.response.SignUpResponseDto;
import com.touchpoint.kh.user.model.dto.response.SocialSignUpResponseDto;
import com.touchpoint.kh.user.model.vo.Certification;
import com.touchpoint.kh.user.model.vo.User;
import com.touchpoint.kh.user.model.vo.UserSocial;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserSocialServiceImple implements UserSocialService{

	private final UserRepository userRepository;
	private final UserSocialRepository userSocialRepository;
	private final UserMapper userMapper;
	
    @Transactional
	@Override
	public void saveSocialUser(String provider, String providerUserId, String email, String username) {
  	
        Optional<UserSocial> existingSocialUser = userSocialRepository.findByProviderAndProviderUserId(provider, providerUserId);
        if (existingSocialUser.isPresent()) return;
        
        User user = userRepository.findByEmail(email);
        if(user == null) {
        			user = User.builder()
        			        	.userId(UUID.randomUUID().toString().replace("-", "").substring(0, 20)) // 하이픈 제거 후 첫 20자만 사용
			                    .name(username)
			                    .email(email)
			                    .build();
        	
            userRepository.save(user);
        }
        
        UserSocial userSocial = UserSocial.builder()
                .provider(provider)
                .providerUserId(providerUserId)
                .userId(user.getUserId())
                .build();
        	userSocialRepository.save(userSocial);
	}

    @Transactional
	@Override
	public ResponseEntity<? super SocialSignUpResponseDto> socialSignUp(SocialSignUpRequestDto dto) {

		try {
			String providerUserId = dto.getUserId();
			String email = dto.getEmail();
			String name = dto.getUsername();
			
			User user = userMapper.findUserByProviderUserId(providerUserId);
			if(user == null) return SocialSignUpResponseDto.socialAuthFail();
			
			user = User.builder()
						.email(email)
						.name(name)
						.adAgreed(dto.getAdAgreed())
						.phoneNo(dto.getPhone())
						.build();
			
			userRepository.save(user);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return SocialSignUpResponseDto.success();
	}
    
	

	
}
