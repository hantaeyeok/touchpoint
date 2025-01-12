package com.touchpoint.kh.user.model.service;

import java.time.LocalDateTime;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.touchpoint.kh.common.ResponseData;
import com.touchpoint.kh.user.model.dao.LoginAttemptRepository;
import com.touchpoint.kh.user.model.dao.UserRepository;
import com.touchpoint.kh.user.model.dao.UserSessionRepository;
import com.touchpoint.kh.user.model.vo.LoginAttempt;
import com.touchpoint.kh.user.model.vo.LoginRequest;
import com.touchpoint.kh.user.model.vo.User;
import com.touchpoint.kh.user.model.vo.UserDto;
import com.touchpoint.kh.user.model.vo.UserSession;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImple implements UserService{

	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final LoginAttemptRepository loginAttemptRepository;
	private final UserSessionRepository userSessionRepository;
	private final GoogleRecaptchaService googleRecaptchaService;
	
	//user id checked
	@Override
	public Boolean userIdChecked(String userId) {
		return userRepository.findByUserId(userId).isEmpty();
	}
	
	@Override
	public Boolean userEmailChecked(String email) {
		return userRepository.findByEmail(email).isEmpty();
	}
	
	
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
	
	
	@Override
	public ResponseData validateLogin(LoginRequest lgoinRequest) {

	    // 사용자 정보 조회
		User user = userRepository.findByUserId(
				lgoinRequest.getUserId())
				  			.orElseThrow(() -> new RuntimeException("사용자 ID를 찾을 수 없습니다."));

	    // 로그인 시도 조회 및 초기화
	    LoginAttempt attempt = loginAttemptRepository.findByUserId(lgoinRequest.getUserId())
	            .orElseGet(() -> LoginAttempt.builder()
	                    .userId(lgoinRequest.getUserId())
	                    .failedLoginCnt(0)
	                    .captchaActive("N")
	                    .build());

	    // 20회 이상 로그인 잠금
	    if (attempt.getFailedLoginCnt() >= 20) {
	    	user.setUserSt("N");
	    	userRepository.save(user);
	    		    	
            return ResponseData.builder()
                    .message("계정이 잠겼습니다. 관리자에게 문의하세요.")
                    .data(null)
                    .build();
        }

		 // 5회 이상 구글 캡쳐 api start
		if(attempt.getFailedLoginCnt() >=5 &&
				"Y".equals(attempt.getFailedLoginCnt())) {
			boolean captchaVerified =googleRecaptchaService.verifyRecaptcha(lgoinRequest.getCaptchaToken());
			if(!captchaVerified) {
				return ResponseData.builder().message("캡차 검증에 실패했습니다. 다시 시도하세요").data(null).build();
			}
			
		}

	    
	    if (!checkPassword(lgoinRequest.getPassword(), user.getPassword())) {
            return handleFailedLogin(attempt);
        }
	    
	    
	    resetFailedLoginAttempts(attempt, user);
        closeActiveSessions(user.getUserId()); // 중복 세션 검증 및 종료
        createSession(user.getUserId()); // 새로운 세션 생성
	    
        return ResponseData.builder()
                .message("로그인 성공")
                .data(user)
                .build();
	}
	
	
	private void resetFailedLoginAttempts(LoginAttempt attempt, User user) {
        attempt.setFailedLoginCnt(0);
        attempt.setCaptchaActive("N");
        user.setUserSt("Y");
        userRepository.save(user);
        loginAttemptRepository.save(attempt);
    }
	

	private boolean checkPassword(String inputPassword, String storedPassword) {
	    return bCryptPasswordEncoder.matches(inputPassword, storedPassword);
	}

	private ResponseData handleFailedLogin(LoginAttempt attempt) {
	    attempt.setFailedLoginCnt(attempt.getFailedLoginCnt() + 1);

	    if (attempt.getFailedLoginCnt() >= 5) {
	        attempt.setCaptchaActive("Y");
	        attempt.setCaptchaTimer(LocalDateTime.now());
	    }

	    loginAttemptRepository.save(attempt);

	    return ResponseData.builder()
	            .message("비밀번호가 일치하지 않습니다.")
	            .data(null)
	            .build();
	}

	private void closeActiveSessions(String userId) {
	    userSessionRepository.findByUserIdAndSessionStatus(userId, "A")
	            .ifPresent(session -> {
	                session.setSessionStatus("I");
	                userSessionRepository.save(session);
	            });
	}

	private void createSession(String userId) {
	    UserSession session = UserSession.builder()
	            .userId(userId)
	            .sessionStatus("A")
	            .loginTime(LocalDateTime.now())
	            .build();
	    userSessionRepository.save(session);
	}


	
	
	/*

	@Override
	public ResponseData validateLogin(LoginRequest loginRequest) {
		
		//로그인 검증시 필요 요구사항
		/* 같은 아이디 로그인 시도 5회 이상 캡쳐
		 * 비밀번호 검증
		 * 중복세션 검증
		 * 새로운 세션 생성
		 * 
		
		//사용자 정보 조회
		User user =  userRepository.findByUserId(loginRequest.getUserId()).orElseThrow(() -> new RuntimeException("사용자 id 못찾아여"));
		
		// 로그인 시도 테이블 조회
		LoginAttempt loginAttempt = loginAttemptRepository.findByUserId(user.getUserId()) .orElseGet(() -> createLoginAttempt(user.getUserId()));
		
		
		 if ("Y".equals(loginAttempt.getCaptchaActive())) {
		        if (LocalDateTime.now().isBefore(loginAttempt.getCaptchaTimer().plusHours(1))) {
		            // 캡차 활성화 상태에서 1시간 이내
		            return ResponseData.builder()
		                    .message("캡차가 활성화되어 있습니다. 캡차를 해결하고 다시 시도하세요.")
		                    .data(null)
		                    .build();
		        } else {
		            // 캡차 활성화 이후 1시간 경과 시 계정 잠금 처리
		            return ResponseData.builder()
		                    .message("계정이 잠겼습니다. 관리자에게 문의하세요.")
		                    .data(null)
		                    .build();
		        }
		    }
		 
		 
		boolean passwordValid = bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword());
		
		if (!passwordValid) {
	        loginAttempt.setFailedLoginCnt(loginAttempt.getFailedLoginCnt() + 1);
	        if (loginAttempt.getFailedLoginCnt() >= 5) {
	            loginAttempt.setCaptchaActive("Y");
	            loginAttempt.setCaptchaTimer(LocalDateTime.now().plusHours(1));
	        }
	        loginAttemptRepository.save(loginAttempt);
	        return ResponseData.builder()
	                .message("비밀번호가 일치하지 않습니다.")
	                .data(null)
	                .build();
	    } else {
	        // 비밀번호 검증 성공 시 시도 초기화
	        loginAttempt.setFailedLoginCnt(0);
	        loginAttempt.setCaptchaActive("N");
	        loginAttempt.setCaptchaTimer(null);
	        loginAttemptRepository.save(loginAttempt);
	    }
		
		userSessionRepository.findByUserIdAndSessionStatus(user.getUserId(), "A")
        .ifPresent(session -> {
            session.setSessionStatus("I");
            userSessionRepository.save(session);
        });
		
		// 새로운 세션 생성
	    UserSession userSession = UserSession.builder()
	            .userId(user.getUserId())
	            .sessionStatus("A")
	            .loginTime(LocalDateTime.now())
	            .build();
	    userSessionRepository.save(userSession);
	
	    //sessionRegistry.registerNewSession(loginRequest.getSessionId(), user);

	    return ResponseData.builder()
	            .message("로그인 성공")
	            .data(user)
	            .build();	}

	
	private LoginAttempt createLoginAttempt(String userId) {
		return LoginAttempt.builder() 
				.userId(userId) 
				.failedLoginCnt(0) 
				.captchaActive("N") 
				.build();
	}
	
	private void validatePassword(String rawPassword, String encodedPassword, LoginAttempt loginAttempt) {

	}

	
	
	*/
	




	



}
