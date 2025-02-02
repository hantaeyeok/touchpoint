package com.touchpoint.kh.user.model.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.touchpoint.kh.user.model.dao.LoginAttemptRepository;
import com.touchpoint.kh.user.model.dao.UserRepository;
import com.touchpoint.kh.user.model.dto.request.SignInRequestDto;
import com.touchpoint.kh.user.model.dto.response.SignInResponseDto;
import com.touchpoint.kh.user.model.vo.LoginAttempt;
import com.touchpoint.kh.user.model.vo.User;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginValidationService {

	private final GoogleRecaptchaService googleRecaptchaService;
	private final LoginAttemptRepository loginAttemptRepository;
	private final UserRepository userRepository;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@Transactional
	public ResponseEntity<SignInResponseDto> validateLogin(User user, SignInRequestDto dto) {
	
		String userId = user.getUserId();
		String password = dto.getPassword();
		String encodedPassword = user.getPassword();
	
		LoginAttempt attempt = loginAttemptRepository.findByUserId(userId);
		if(attempt==null) {
			attempt = LoginAttempt.builder().userId(userId).failedLoginCnt(0).captchaActive("N").build();
			loginAttemptRepository.save(attempt);
		}
		
		if(attempt.getFailedLoginCnt() >= 20) {
			user.setUserSt("N");
			userRepository.save(user);
			return SignInResponseDto.accountLocked(attempt.getFailedLoginCnt(),attempt.getCaptchaActive());
		}
		

		boolean isMatched = passwordEncoder.matches(password, encodedPassword);
		if(!isMatched) {
			attempt.setFailedLoginCnt(attempt.getFailedLoginCnt() + 1);
			if(attempt.getFailedLoginCnt() > 5) {
				attempt.setCaptchaActive("Y"); 
			}	
			loginAttemptRepository.save(attempt);
			return SignInResponseDto.signInFail(attempt.getFailedLoginCnt(),attempt.getCaptchaActive());
		}
		

	    if (attempt.getFailedLoginCnt() >= 5 && "Y".equals(attempt.getCaptchaActive())) {
	        boolean isCaptchaValid = googleRecaptchaService.verifyRecaptcha(dto.getCaptchaToken());
	        if(!isCaptchaValid) return SignInResponseDto.captchaFail(attempt.getFailedLoginCnt(),attempt.getCaptchaActive());
	        
	        if (!passwordEncoder.matches(password, encodedPassword)) {
	            attempt.setFailedLoginCnt(attempt.getFailedLoginCnt() + 1);
	            loginAttemptRepository.save(attempt);
	            return SignInResponseDto.captchaSuccessButPasswordFail(attempt.getFailedLoginCnt(), attempt.getCaptchaActive());
	        }
	    }
		
        
	    if(isMatched) {
	    	loginAttemptRepository.deleteByUserId(userId);
		}
	    
	    return SignInResponseDto.validataSuccess(attempt.getFailedLoginCnt(), attempt.getCaptchaActive());
	}
	
}
