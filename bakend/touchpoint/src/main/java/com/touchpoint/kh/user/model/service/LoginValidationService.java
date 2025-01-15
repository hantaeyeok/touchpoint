package com.touchpoint.kh.user.model.service;

import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.touchpoint.kh.common.ResponseData;
import com.touchpoint.kh.user.model.dao.LoginAttemptRepository;
import com.touchpoint.kh.user.model.dao.UserRepository;
import com.touchpoint.kh.user.model.vo.LoginAttempt;
import com.touchpoint.kh.user.model.vo.LoginRequest;
import com.touchpoint.kh.user.model.vo.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginValidationService {

	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final LoginAttemptRepository loginAttemptRepository;
	private final GoogleRecaptchaService googleRecaptchaService;
	private final UserRepository userRepository;
	
	public ResponseData validateLogin(User user, LoginRequest loginRequest) {
	
		LoginAttempt attempt = createAttempt(loginRequest); //로그인 시도 조회 및 초기화 사용자가 userId인지 phone 인지 검증
		
        ResponseData lockResponse = lockAccount(user, attempt); //로그인 잠금 처리
        if (lockResponse != null) {
            return lockResponse;
        }
        
        ResponseData passwordResponse = passwordValid(loginRequest.getPassword(), user.getPassword(), attempt); 
        if (passwordResponse != null) {
            return passwordResponse;
        }
		
        ResponseData captchaResponse = captchaRequired(attempt, loginRequest);
        if (captchaResponse != null) {
            return captchaResponse;
        }
        
        resetLoginAttempt(attempt); // 로그인 성공 처리 (시도 초기화)
		
		return ResponseData.builder().message("로그인 성공").data(user).build();
	}
	
	//비번 검증
	private ResponseData passwordValid(String inputPassword, String storedPassword, LoginAttempt attempt) {
	    if (!bCryptPasswordEncoder.matches(inputPassword, storedPassword)) {
	        // 실패 카운트 증가
	        attempt.setFailedLoginCnt(attempt.getFailedLoginCnt() + 1);

	        // 5회 이상 실패 시 캡차 활성화
	        if (attempt.getFailedLoginCnt() >= 5) {
	            attempt.setCaptchaActive("Y");
	        }

	        // 변경된 로그인 시도 정보 저장
	        loginAttemptRepository.save(attempt);

	        // 실패 응답 반환
	        return ResponseData.builder()
	                .message("비밀번호가 일치하지 않습니다.")
	                .data(Map.of(
	                        "failedLoginCnt", attempt.getFailedLoginCnt(),
	                        "accountLocked", false
	                    ))
	                .build();
	    }

	    return null; // 비밀번호가 일치하는 경우 null 반환
	}
	
	
	//로그인 시도 조회 및 초기화
	private LoginAttempt createAttempt(LoginRequest loginRequest) {
	    String userId;

	    // 조건에 따라 userId 값을 설정
	    if ("phone".equals(loginRequest.getUserType())) {
	        User user = userRepository.findByPhoneNo(loginRequest.getUserIdOrPhone())
	                .orElseThrow(() -> new RuntimeException("전화번호와 일치하는 사용자 ID가 없습니다."));
	        userId = user.getUserId(); // userId는 최종값으로 설정
	    } else if ("userId".equals(loginRequest.getUserType())) {
	        userId = loginRequest.getUserIdOrPhone();
	    } else {
	        throw new IllegalArgumentException("잘못된 userType입니다.");
	    }

	    // LoginAttempt 조회 또는 생성 후 저장
	    return loginAttemptRepository.findByUserId(userId)
	            .orElseGet(() -> loginAttemptRepository.save(
	                    LoginAttempt.builder()
	                            .userId(userId)
	                            .failedLoginCnt(0)
	                            .captchaActive("N")
	                            .build()
	            ));
	}
	
	//계정 잠금 여부 확인
	private ResponseData lockAccount(User user, LoginAttempt attempt) {
		if(attempt.getFailedLoginCnt() >= 20) {
			user.setUserSt("N");
			userRepository.save(user);
			
			return ResponseData.builder()
	                .message("계정이 잠겼습니다. 관리자에게 문의하세요.")
	                .data(Map.of(
	                        "failedLoginCnt", attempt.getFailedLoginCnt(),
	                        "accountLocked", true
	                    ))
	                .build();
		}
		return null;
		
	}
	

	// 캡차 검증 처리
	private ResponseData captchaRequired(LoginAttempt attempt, LoginRequest loginRequest) {
	    // 실패 횟수가 5 이상이고 캡차가 활성화된 경우
	    if (attempt.getFailedLoginCnt() >= 5 && "Y".equals(attempt.getCaptchaActive())) {
	        // 캡차 검증
	        boolean isCaptchaValid = googleRecaptchaService.verifyRecaptcha(loginRequest.getCaptchaToken());
	        if (!isCaptchaValid) {
	            return ResponseData.builder()
	                    .message("캡차 검증에 실패했습니다. 다시 시도하세요.")
	                    .data(Map.of(
	                            "failedLoginCnt", attempt.getFailedLoginCnt(),
	                            "captchaRequired", true
	                        ))
	                    .build();
	        }
	    }
	    return null; // 캡차 검증이 필요 없거나 성공한 경우
	}
	
	//로그인 성공 시 초기화
	private void resetLoginAttempt(LoginAttempt attempt) {
		attempt.setFailedLoginCnt(0);
        attempt.setCaptchaActive("N");
        loginAttemptRepository.save(attempt);
	}

}
