package com.touchpoint.kh.user.model.service;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.touchpoint.kh.common.ResponseData;
import com.touchpoint.kh.user.model.dao.LoginAttemptRepository;
import com.touchpoint.kh.user.model.dao.UserRepository;
import com.touchpoint.kh.user.model.dto.request.SignInRequestDto;
import com.touchpoint.kh.user.model.dto.response.ResponseDto;
import com.touchpoint.kh.user.model.dto.response.SignInResponseDto;
import com.touchpoint.kh.user.model.vo.LoginAttempt;
import com.touchpoint.kh.user.model.vo.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginValidationService {

	private final LoginAttemptRepository loginAttemptRepository;
	private final GoogleRecaptchaService googleRecaptchaService;
	private final UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	public ResponseEntity<ResponseDto> validateLogin(User user, SignInRequestDto dto) {
	
		String userId = user.getUserId();
		String password = dto.getPassword();
		String encodedPassword = user.getPassword();
		
		LoginAttempt attempt =createLoginAttempt(userId);
		int loginCount = attempt.getFailedLoginCnt();
		
		if(loginCount >= 20) {
			user.setUserSt("N");
			userRepository.save(user);
			return SignInResponseDto.accountLocked();
		}
		
		
		
		boolean isMatched = passwordEncoder.matches(password, encodedPassword);
		if(!isMatched) {
			if(!isMatched) attempt.setFailedLoginCnt(attempt.getFailedLoginCnt() + 1);
			if(loginCount > 5) attempt.setCaptchaActive("Y");	
			loginAttemptRepository.save(attempt);
			return SignInResponseDto.signInFail();
		}
		

	    if (loginCount >= 5 && "Y".equals(attempt.getCaptchaActive())) {
	        boolean isCaptchaValid = googleRecaptchaService.verifyRecaptcha(dto.getCaptchaToken());
	        if(!isCaptchaValid) return SignInResponseDto.captchaFail();
	    }
		
        
	    if(isMatched) {
			loginAttemptRepository.deleteByUserId(userId);
		}
	    
	    return SignInResponseDto.validataSuccess();
	}
	
	
	private LoginAttempt createLoginAttempt(String userId) {
		LoginAttempt attempt = loginAttemptRepository.findByUserId(userId);
		if(attempt == null) {
			attempt = new LoginAttempt(userId);
			loginAttemptRepository.save(attempt);
		}
		return attempt;
	}
	

}
