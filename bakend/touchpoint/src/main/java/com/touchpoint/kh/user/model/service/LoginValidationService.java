package com.touchpoint.kh.user.model.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.touchpoint.kh.user.model.dao.UserMapper;
import com.touchpoint.kh.user.model.dao.UserRepository;
import com.touchpoint.kh.user.model.dto.request.SignInRequestDto;
import com.touchpoint.kh.user.model.dto.response.SignInResponseDto;
import com.touchpoint.kh.user.model.vo.LoginAttemptMy;
import com.touchpoint.kh.user.model.vo.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginValidationService {

	//private final LoginAttemptRepository loginAttemptRepository;
	private final GoogleRecaptchaService googleRecaptchaService;
	private final UserMapper userMapper;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Transactional
	public ResponseEntity<SignInResponseDto> validateLogin(User user, SignInRequestDto dto) {
	
		System.out.println(user);
		String userId = user.getUserId();
		String password = dto.getPassword();
		String encodedPassword = user.getPassword();
	
		LoginAttemptMy attempt = userMapper.attemptFindByUserId(userId);
		if(attempt==null) {
			attempt = LoginAttemptMy.builder().userId(userId).failedLoginCnt(0).captchaActive("N").build();
			userMapper.insertLoginAttempt(attempt);
		}
		
		if(attempt.getFailedLoginCnt() >= 20) {
			user.setUserSt("N");
			userMapper.updateUser(user);
			return SignInResponseDto.accountLocked(attempt.getFailedLoginCnt(),attempt.getCaptchaActive());
		}
		

		boolean isMatched = passwordEncoder.matches(password, encodedPassword);
		log.info("isMatched {}",isMatched);
		if(!isMatched) {
			attempt.setFailedLoginCnt(attempt.getFailedLoginCnt() + 1);
			if(attempt.getFailedLoginCnt() > 5) {
				attempt.setCaptchaActive("Y"); 
			}	
			userMapper.updateLoginAttempt(attempt);
			return SignInResponseDto.signInFail(attempt.getFailedLoginCnt(),attempt.getCaptchaActive());
		}
		

	    if (attempt.getFailedLoginCnt() >= 5 && "Y".equals(attempt.getCaptchaActive())) {
	        boolean isCaptchaValid = googleRecaptchaService.verifyRecaptcha(dto.getCaptchaToken());
	        if(!isCaptchaValid) return SignInResponseDto.captchaFail(attempt.getFailedLoginCnt(),attempt.getCaptchaActive());
	        
	        if (!passwordEncoder.matches(password, encodedPassword)) {
	            log.info("캡차 성공했지만 비밀번호 불일치");
	            attempt.setFailedLoginCnt(attempt.getFailedLoginCnt() + 1);
	            userMapper.updateLoginAttempt(attempt);
	            return SignInResponseDto.captchaSuccessButPasswordFail(attempt.getFailedLoginCnt(), attempt.getCaptchaActive());
	        }
	    
	    
	    
	    }
		
        
	    if(isMatched) {
			userMapper.deleteLoginAttemptByUserId(userId);
		}
	    
	    return SignInResponseDto.validataSuccess(attempt.getFailedLoginCnt(), attempt.getCaptchaActive());
	}
	
}
