package com.touchpoint.kh.user.model.service;

import java.util.Map;

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
import com.touchpoint.kh.user.model.dto.response.CheckCookieResponseDto;
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

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
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
	public ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto, HttpServletResponse response) {
		
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

//			Cookie jwtCookie = new Cookie("authToken", token);
//			jwtCookie.setHttpOnly(true); // 자바스크립트에서 접근 불가
//	        jwtCookie.setSecure(true); // HTTPS에서만 전송 (개발 환경에서는 false로 설정 가능)
//	        jwtCookie.setPath("/"); // 쿠키가 모든 경로에서 유효
//	        jwtCookie.setMaxAge(3600); // 쿠키 유효 시간: 1시간
//	
//
//	        response.addCookie(jwtCookie); // 응답에 쿠키 추가
			 ResponseCookie jwtCookie = ResponseCookie.from("authToken", token)
		                .httpOnly(true)
		                .secure(true) // HTTPS 환경에서는 true로 변경
		                .sameSite("None") // SameSite 설정
		                .path("/") // 모든 경로에서 유효
		                .maxAge(3600) // 1시간 유효
		                .build();

			 response.addHeader("Set-Cookie", jwtCookie.toString());
		        // 응답 헤더에 쿠키 추가
//	        log.info("jwtCookie{}",jwtCookie);
//	        log.info("JWT 쿠키 정보: Name = {}, Value = {}, MaxAge = {}, HttpOnly = {}, Secure = {}", 
//	        	    jwtCookie.getName(), 
//	        	    jwtCookie.getValue(), 
//	        	    jwtCookie.getMaxAge(), 
//	        	    jwtCookie.isHttpOnly(), 
//	        	    jwtCookie.getSecure());
			 log.info("JWT Cookie Created: Name={}, Value={}, MaxAge={}, HttpOnly={}, Secure={}, Path={}, SameSite={}",
		                jwtCookie.getName(),
		                jwtCookie.getValue(),
		                jwtCookie.getMaxAge(),
		                jwtCookie.isHttpOnly(),
		                jwtCookie.isSecure(),
		                jwtCookie.getPath(),
		                jwtCookie.getSameSite());
			 
	        
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
			return ResponseDto.databaseError();

		}
		
		return CheckCertificaionResponseDto.success(user);
	}

//	@Override
//    public ResponseEntity<? super CheckCookieResponseDto> checkAuth(String authToken) {
//		Map<String, String> userInfo =null;
//		try {
//	        // 쿠키에서 JWT 추출 및 검증
//	        String token = null;
//	        if (request.getCookies() != null) {
//	            for (Cookie cookie : request.getCookies()) {
//	                if ("authToken".equals(cookie.getName())) {
//	                    token = cookie.getValue();
//	                    break;
//	                }
//	            }
//	        }
//
//	        if (token == null) return CheckCookieResponseDto.noCookie();  // 쿠키가 없을 경우
//	        Map<String, String> result = jwtProvider.validata(token);
//	        if(result==null) return CheckCookieResponseDto.unauthorized();
// 
//	       userInfo = Map.of("userId", result.get("userId"), "role", result.get("role"));
//
//	        // 성공 응답 반환
//
//	    } catch (Exception e) {
//	        e.printStackTrace();
//			return ResponseDto.databaseError();
//	    }
//        return CheckCookieResponseDto.success(userInfo);
//
//    }
	@Override
	public ResponseEntity<? super CheckCookieResponseDto> checkAuth(String authToken) {
	    Map<String, String> userInfo = null;

	    try {
	        // JWT 검증 결과
	        Map<String, String> result = jwtProvider.validata(authToken);

	        // 토큰이 유효하지 않은 경우
	        if (result == null) {
	            return CheckCookieResponseDto.unauthorized();
	        }

	        // 사용자 정보 맵핑
	        userInfo = Map.of(
	            "userId", result.get("userId"),
	            "role", result.get("role")
	        );

	    } catch (Exception e) {
	        // 기타 예외 처리
	        e.printStackTrace();
	        return ResponseDto.databaseError();
	    }

	    // 성공 응답 반환
	    return CheckCookieResponseDto.success(userInfo);
	}

	
	



	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
	

}
