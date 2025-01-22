package com.touchpoint.kh.user.model.service;


import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.touchpoint.kh.user.common.CertificationNumber;
import com.touchpoint.kh.user.model.dao.CertificationRepository;
import com.touchpoint.kh.user.model.dao.UserMapper;
import com.touchpoint.kh.user.model.dao.UserRepository;
import com.touchpoint.kh.user.model.dto.request.EmailCertificaionRequsetDto;
import com.touchpoint.kh.user.model.dto.request.SignInRequestDto;
import com.touchpoint.kh.user.model.dto.request.SignUpRequestDto;
import com.touchpoint.kh.user.model.dto.request.check.CheckCertificaionRequestDto;
import com.touchpoint.kh.user.model.dto.request.check.EmailCheckRequestDto;
import com.touchpoint.kh.user.model.dto.request.check.IdCheckRequestDto;
import com.touchpoint.kh.user.model.dto.request.check.PhoneCheckRequestDto;
import com.touchpoint.kh.user.model.dto.request.find.FindIdRequestDto;
import com.touchpoint.kh.user.model.dto.request.find.FindPasswordRequestDto;
import com.touchpoint.kh.user.model.dto.response.EmailCertificaionResponseDto;
import com.touchpoint.kh.user.model.dto.response.ResponseDto;
import com.touchpoint.kh.user.model.dto.response.SignInResponseDto;
import com.touchpoint.kh.user.model.dto.response.SignUpResponseDto;
import com.touchpoint.kh.user.model.dto.response.check.CheckCertificaionResponseDto;
import com.touchpoint.kh.user.model.dto.response.check.EmailCheckResponseDto;
import com.touchpoint.kh.user.model.dto.response.check.IdCheckResponseDto;
import com.touchpoint.kh.user.model.dto.response.check.PhoneCheckResponsetDto;
import com.touchpoint.kh.user.model.dto.response.find.FindIdResponseDto;
import com.touchpoint.kh.user.model.dto.response.find.FindPasswordResponseDto;
import com.touchpoint.kh.user.model.vo.Certification;
import com.touchpoint.kh.user.model.vo.User;
import com.touchpoint.kh.user.provider.EmailProvider;
import com.touchpoint.kh.user.provider.JwtProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final EmailProvider emailProvider;
	private final CertificationRepository certificationRepository;
	private final JwtProvider jwtProvider;
	private final LoginValidationService loginValidationService;
	
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	//check
	@Override
	public ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto) {
		try {
			String userId = dto.getUserId();
			boolean isExisId = userRepository.existsByUserId(userId);
			if(isExisId) return IdCheckResponseDto.duplicatId();		
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		return IdCheckResponseDto.success();
	}
	
	@Override
	public ResponseEntity<? super PhoneCheckResponsetDto> phoneCheck(PhoneCheckRequestDto dto) {
		try {
			String phone = dto.getPhone().replace("-", "");
			boolean isExisId = userRepository.existsByPhoneNo(phone); 
			if(isExisId) return PhoneCheckResponsetDto.duplicatPhone();
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		return PhoneCheckResponsetDto.success();
	}

	@Override
	public ResponseEntity<? super EmailCheckResponseDto> emailCheck(EmailCheckRequestDto dto) {
		try {
			String email = dto.getEmail();
			boolean isExisId = userRepository.existsByEmail(email);
			if(isExisId) return EmailCheckResponseDto.duplicatEmail();
				
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		return EmailCheckResponseDto.success();
	}
	
	@Override
	public ResponseEntity<? super EmailCertificaionResponseDto> emailCertification(EmailCertificaionRequsetDto dto) {
		try {
			
			String userId = dto.getUserId();
			String email = dto.getEmail();
			
			boolean isExisId = userRepository.existsByUserId(userId);
			if(isExisId) return EmailCertificaionResponseDto.dublicateId();
			
			String certificaionNumber = CertificationNumber.getCertificationNumber();
			
			boolean isSuccessed = emailProvider.sendCerttificaionMail(email, certificaionNumber);
			if(!isSuccessed) return EmailCertificaionResponseDto.mailSendFail();
			
			Certification certification = new Certification(userId, email, certificaionNumber);
			certificationRepository.save(certification);
				
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		
		return EmailCertificaionResponseDto.success();
	}

	@Override
	public ResponseEntity<? super CheckCertificaionResponseDto> checkCertificaion(CheckCertificaionRequestDto dto) {

		try {
			String userId = dto.getUserId();
			String email = dto.getEmail();
			String certificaionNumber = dto.getCertificationNumber();
			
			Certification certification = certificationRepository.findByUserId(userId);
			if(certification == null) return CheckCertificaionResponseDto.certificaionFail();
			
			boolean isMatch = certification.getEmail().equals(email) && certification.getCertificationNumber().equals(certificaionNumber);
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
			
			String userId = dto.getUserId();
			boolean isExistId = userRepository.existsByUserId(userId);
			if(isExistId) return SignUpResponseDto.duplicateId();
			
			String email = dto.getEmail();
			String certificationNumber = dto.getCertificationNumber();
			
			Certification certification = certificationRepository.findByUserId(userId);
			
			boolean isMatched = certification.getEmail().equals(email) && certification.getCertificationNumber().equals(certificationNumber);
			if(!isMatched) return SignUpResponseDto.certificaionFail();
			
			String password = dto.getPassword();
			String encodedPassword = passwordEncoder.encode(password);
			dto.setPassword(encodedPassword);
			
			User user = new User(dto);
			userRepository.save(user);
		
			certificationRepository.deleteByUserId(userId);
			
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
			
			String userIdOrPhone = dto.getUserIdOrPhone().replace("-", "");
			User user = userMapper.findByUserIdOrPhone(userIdOrPhone);
			if(user == null) return SignInResponseDto.signInFail();

			ResponseEntity<SignInResponseDto> validationResponse = 
					loginValidationService.validateLogin(user, dto);
			
			 if (!validationResponse.getStatusCode().is2xxSuccessful()) {
				 log.info("validationResponse {}",validationResponse);
				 log.info("Validation Response: Status={}, Body={}", validationResponse.getStatusCode(), validationResponse.getBody());

		            return validationResponse;
		        }
	        
			token = jwtProvider.create(user.getUserId());

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		
		}
		
		
		return SignInResponseDto.success(token);
	}

	@Override
	public ResponseEntity<? super FindIdResponseDto> findId(FindIdRequestDto dto) {
		User user = null;
		try {
			String email = dto.getEmail();
			String phone = dto.getPhone().replace("-", "");
			String userName = dto.getUserName();
			
			// 이메일과 전화번호로 조회
	        if (email != null && !email.isEmpty()) {
	            user = userRepository.findByEmailAndPhoneNo(email, phone);
	        }

	        // 이름과 전화번호로 조회
	        if (user == null && userName != null && !userName.isEmpty()) {
	            user = userRepository.findByUserNameAndPhoneNo(userName, phone);
	        }

	        // 두 조건 모두 만족하지 못한 경우
	        if (user == null) {
	            return FindIdResponseDto.findFail();
	        }
	        
		} catch (Exception e) {
			e.printStackTrace();
	        return ResponseDto.databaseError();
		}
		
        return FindIdResponseDto.success(user.getUserId());

	}

	@Override
	public ResponseEntity<? super FindPasswordResponseDto> findPassword(FindPasswordRequestDto dto) {
		
		try {
			String userIdOrPhone = dto.getUserIdOrPhone().replace("-", "");
	        String email = dto.getEmail();
	        User user = userRepository.findByUserIdAndEmailAndPhoneNo(userIdOrPhone, email);
	        if (user == null) { 
	            return FindPasswordResponseDto.resetFail(); // 사용자 정보 없음
	        }
			
	        String certificaionNumber = CertificationNumber.getCertificationNumber();
			
			boolean isSuccessed = emailProvider.sendCerttificaionMail(email, certificaionNumber);
			if(!isSuccessed) return EmailCertificaionResponseDto.mailSendFail();
			
			Certification certification = new Certification(userId, email, certificaionNumber);
			certificationRepository.save(certification);
	        
	        
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		
		
		return null;
	}

	




	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	

}
