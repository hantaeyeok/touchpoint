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

import jakarta.transaction.Transactional;
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

	@Transactional
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
			
			User user = User.builder()
							.userId(userId)
							.password(encodedPassword)
							.email(email)
							.phoneNo(dto.getPhone().replace("-", ""))
							.name(dto.getUserName())
							.adAgreed(dto.getAdAgreed())
							.build();
			
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
	        
			token = jwtProvider.create(user.getUserId(),user.getUserRole());

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
			
			String userName = dto.getUserName();
	        String phone = dto.getPhone() != null ? dto.getPhone().replace("-", "") : null; // 전화번호 형식 처리
			String email = dto.getEmail();

			if (email != null && !email.isEmpty() && userName != null && !userName.isEmpty()) {
				email = dto.getEmail();
	            user = userRepository.findByNameAndEmail(userName, email);
	        }

	        if (user == null && userName != null && !userName.isEmpty() && phone != null && !phone.isEmpty()) {
				phone = dto.getPhone().replace("-", "");
	            user = userRepository.findByNameAndPhoneNo(userName, phone);
	        }

	        if (user == null) return FindIdResponseDto.findFail();
	        if (user.getSocialUser().equals("Y")) return FindIdResponseDto.findSocialFail();
	        
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
	        
	        User user = userMapper.findByUserIdAndEmailAndPhoneNo(userIdOrPhone, email);
	        if (user == null) return FindPasswordResponseDto.resetFail(); // 사용자 정보 없음

	        String certificaionNumber = CertificationNumber.getCertificationNumber();
			
			boolean isSuccessed = emailProvider.sendCerttificaionMail(email, certificaionNumber);
			if(!isSuccessed) return FindPasswordResponseDto.mailSendFail();
			
			Certification certification = new Certification(user.getUserId(), email, certificaionNumber);
			certificationRepository.save(certification); 
	        
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseDto.databaseError();
		}
		
		return FindPasswordResponseDto.success();
	}

	@Override
	public ResponseEntity<? super CheckCertificaionResponseDto> passwordCertification(CheckCertificaionRequestDto dto) {
		User user = null;
		try {
			String userIdOrPhone = dto.getUserId();
			String email = dto.getEmail();
			String certificaionNumber = dto.getCertificationNumber();
			
			user = userMapper.findByUserIdOrPhone(userIdOrPhone);
			if(user == null) return CheckCertificaionResponseDto.validaionFail();
			
			String userId = user.getUserId();
			Certification certification = certificationRepository.findByUserId(userId);
			if(certification == null) return CheckCertificaionResponseDto.certificaionFail();
			
			boolean isMatch = certification.getEmail().equals(email) && certification.getCertificationNumber().equals(certificaionNumber);
			if(!isMatch) return CheckCertificaionResponseDto.certificaionFail();
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return CheckCertificaionResponseDto.success(user);
	}


	
	



	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	

}
