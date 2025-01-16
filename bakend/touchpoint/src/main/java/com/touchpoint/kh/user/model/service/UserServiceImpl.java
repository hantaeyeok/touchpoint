package com.touchpoint.kh.user.model.service;


import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.touchpoint.kh.common.ResponseData;
import com.touchpoint.kh.user.common.CertificationNumber;
import com.touchpoint.kh.user.email.EmailProvider;
import com.touchpoint.kh.user.jwt.JwtProvider;
import com.touchpoint.kh.user.model.dao.CertificaionRepository;
import com.touchpoint.kh.user.model.dao.LoginAttemptRepository;
import com.touchpoint.kh.user.model.dao.UserMapper;
import com.touchpoint.kh.user.model.dao.UserRepository;
import com.touchpoint.kh.user.model.dao.UserSessionRepository;
import com.touchpoint.kh.user.model.dto.request.CheckCertificaionRequestDto;
import com.touchpoint.kh.user.model.dto.request.EmailCertificaionRequsetDto;
import com.touchpoint.kh.user.model.dto.request.IdCheckRequestDto;
import com.touchpoint.kh.user.model.dto.request.SignInRequestDto;
import com.touchpoint.kh.user.model.dto.response.CheckCertificaionResponseDto;
import com.touchpoint.kh.user.model.dto.response.EmailCertificaionResponseDto;
import com.touchpoint.kh.user.model.dto.response.IdCheckResponseDto;
import com.touchpoint.kh.user.model.dto.response.ResponseDto;
import com.touchpoint.kh.user.model.dto.response.SignInResponseDto;
import com.touchpoint.kh.user.model.dto.response.SignUpRequestDto;
import com.touchpoint.kh.user.model.dto.response.SignUpResponseDto;
import com.touchpoint.kh.user.model.vo.Certificaion;
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
	
	private final EmailProvider emailProvider;
	private final CertificaionRepository certificaionRepository;
	private final JwtProvider jwtProvider;
	
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
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


	

	
	@Override
	public ResponseEntity<? super IdCheckResponseDto> idcheck(IdCheckRequestDto dto) {
		try {
			String userId = dto.getId();
			boolean isExisId = userRepository.existByUserId(userId);
			if(isExisId) return IdCheckResponseDto.duplicatId();
				
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		return IdCheckResponseDto.success();
	}

	@Override
	public ResponseEntity<? super EmailCertificaionResponseDto> emailCertification(EmailCertificaionRequsetDto dto) {
		try {
			
			String userId = dto.getId();
			String email = dto.getEmail();
			
			boolean isExisId = userRepository.existByUserId(userId);
			if(isExisId) return EmailCertificaionResponseDto.dublicateId();
			
			String certificaionNumber = CertificationNumber.getCertificationNumber();
			
			boolean isSuccessed = emailProvider.sendCerttificaionMail(email, certificaionNumber);
			if(!isSuccessed) return EmailCertificaionResponseDto.mailSendFail();
			
			Certificaion certificaion = new Certificaion(userId, email, certificaionNumber);
			certificaionRepository.save(certificaion);
				
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		
		return EmailCertificaionResponseDto.success();
	}

	@Override
	public ResponseEntity<? super CheckCertificaionResponseDto> checkCertificaion(CheckCertificaionRequestDto dto) {

		try {
			String userId = dto.getId();
			String email = dto.getEmail();
			String certificaionNumber = dto.getCertificaionNumber();
			
			Certificaion certificaion = certificaionRepository.findByUserId(userId);
			if(certificaion == null) return CheckCertificaionResponseDto.certificaionFail();
			
			boolean isMatch = certificaion.getEmail().equals(email) && certificaion.getCertificationNumber().equals(certificaionNumber);
			if(!isMatch) return CheckCertificaionResponseDto.certificaionFail();
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		
		}
		
		return CheckCertificaionResponseDto.success();
	}

	@Override
	public ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto) {

		try {
			
			String userId = dto.getId();
			boolean isExistId = userRepository.existByUserId(userId);
			if(isExistId) return SignUpResponseDto.duplicateId();
			
			String email = dto.getEmail();
			String certificationNumber = dto.getCertificationNumber();
			
			Certificaion certificaion = certificaionRepository.findByUserId(userId);
			
			boolean isMatched = certificaion.getEmail().equals(email) && certificaion.getCertificationNumber().equals(certificaionNumber);
			if(!isMatched) return SignUpResponseDto.certificaionFail();
			
			String password = dto.getPassword();
			String encodedPassword = passwordEncoder.encode(password);
			dto.setPassword(encodedPassword);
			
			User user = new User(dto);
			userRepository.save(user);
		
			//certificaionRepository.delete(certificaion);
			certificaionRepository.deleteByUserId(userId);
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return SignUpResponseDto.success();
	}

	@Override
	public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto) {
		
		String token = null;
		
		try {
			
			String userId = dto.getId();
			User user = userRepository.findByUserId(userId);
			if(user == null) return SignInResponseDto.signInFail();
			
			String password = dto.getPassword();
			String encodedPassword = user.getPassword();
			
			boolean isMatched = passwordEncoder.matches(password, encodedPassword);
			if(!isMatched) return SignInResponseDto.signInFail();
			
			token = jwtProvider.create(userId);
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		
		}
		
		
		return SignInResponseDto.success(token);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	

}
