package com.touchpoint.kh.user.model.service;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.touchpoint.kh.common.ResponseData;
import com.touchpoint.kh.user.model.dao.LoginAttemptRepository;
import com.touchpoint.kh.user.model.dao.UserMapper;
import com.touchpoint.kh.user.model.dao.UserRepository;
import com.touchpoint.kh.user.model.dao.UserSessionRepository;
import com.touchpoint.kh.user.model.vo.LoginRequest;
import com.touchpoint.kh.user.model.vo.User;
import com.touchpoint.kh.user.model.vo.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final LoginAttemptRepository loginAttemptRepository;
	private final UserSessionRepository userSessionRepository;
	private final LoginValidationService loginValidationService;
	private final UserMapper userMapper;
	
	//user id 중복확인
	@Override
	public Boolean userIdChecked(String userId) {
		return userRepository.findByUserId(userId).isEmpty();
	}
	
	//Email 중복 확인
	@Override
	public Boolean userEmailChecked(String email) {
		return userRepository.findByEmail(email).isEmpty();
	}
	
	//phone 중복 확인
	@Override
	public Boolean userPhoneChecked(String phone) {
		String replacePhone = phone.replaceAll("-", "");
		return userRepository.findByPhoneNo(replacePhone).isEmpty();
	}
	
	
	
	// 일반 사용자 회원가입
	@Override
	public User signupGeneralUser(UserDto userDto) {		    
	    String encodePassword = bCryptPasswordEncoder.encode(userDto.getPassword());
		User user = User.builder() //Dto -> Vo -> save
		                .userId(userDto.getUserId())
		                .password(encodePassword) 
		                .email(userDto.getEmail())           
		                .phoneNo(userDto.getPhone().replaceAll("-", "")) //전화번호 front에서 010-1234-1234 or 01012341234              
		                .name(userDto.getUsername())
		                .adAgreed(userDto.isAdAgreed() ? "Y" : "N")
		                .joinDt(java.time.LocalDate.now())
		                .userSt("Y")
		                .socialUser("N")
		                .build();
		
		return userRepository.save(user);
	}
	
	//로그인 검증
	@Override
	public ResponseData validateLogin(LoginRequest lgoinRequest) {
		User user = userMapper.findByUserIdOrPhone(lgoinRequest.getUserIdOrPhone())
						.orElseThrow(() -> new RuntimeException("사용자id phone둘다 실패임"));

				//		.orElseThrow(() -> new RuntimeException("사용자id phone둘다 실패임"));
		//User user = userRepository.findByUserIdOrPhone(lgoinRequest.getUserIdOrPhone())
		//		.orElseThrow(() -> new RuntimeException("사용자id phone둘다 실패임"));

		return loginValidationService.validateLogin(user, lgoinRequest);
	}

	
	

}
