package com.touchpoint.kh.user.contorller;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.touchpoint.kh.user.model.dto.request.EmailCertificaionRequsetDto;
import com.touchpoint.kh.user.model.dto.request.SignInRequestDto;
import com.touchpoint.kh.user.model.dto.request.SignUpRequestDto;
import com.touchpoint.kh.user.model.dto.request.SocialSignUpRequestDto;
import com.touchpoint.kh.user.model.dto.request.check.CheckCertificaionRequestDto;
import com.touchpoint.kh.user.model.dto.request.check.EmailCheckRequestDto;
import com.touchpoint.kh.user.model.dto.request.check.IdCheckRequestDto;
import com.touchpoint.kh.user.model.dto.request.check.PhoneCheckRequestDto;
import com.touchpoint.kh.user.model.dto.request.find.FindIdRequestDto;
import com.touchpoint.kh.user.model.dto.request.find.FindPasswordRequestDto;
import com.touchpoint.kh.user.model.dto.response.CheckCookieResponseDto;
import com.touchpoint.kh.user.model.dto.response.EmailCertificaionResponseDto;
import com.touchpoint.kh.user.model.dto.response.SignInResponseDto;
import com.touchpoint.kh.user.model.dto.response.SignUpResponseDto;
import com.touchpoint.kh.user.model.dto.response.SocialSignUpResponseDto;
import com.touchpoint.kh.user.model.dto.response.check.CheckCertificaionResponseDto;
import com.touchpoint.kh.user.model.dto.response.check.EmailCheckResponseDto;
import com.touchpoint.kh.user.model.dto.response.check.IdCheckResponseDto;
import com.touchpoint.kh.user.model.dto.response.check.PhoneCheckResponsetDto;
import com.touchpoint.kh.user.model.dto.response.find.FindIdResponseDto;
import com.touchpoint.kh.user.model.dto.response.find.FindPasswordResponseDto;
import com.touchpoint.kh.user.model.service.UserService;
import com.touchpoint.kh.user.model.service.UserSocialService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final UserSocialService userSocialService;
	
	@PostMapping("/check-id")
	public ResponseEntity<? super IdCheckResponseDto> idCheck(
			@RequestBody @Valid IdCheckRequestDto requestBody){
		ResponseEntity<? super IdCheckResponseDto> response = userService.idCheck(requestBody);
		return response;
	}
	
	@PostMapping("/check-email")
	public ResponseEntity<? super EmailCheckResponseDto> emailCheck(
			@RequestBody @Valid EmailCheckRequestDto requestBody){
		ResponseEntity<? super EmailCheckResponseDto> response = userService.emailCheck(requestBody);
		return response;
	}
	
	@PostMapping("/check-phone")
	public ResponseEntity<? super PhoneCheckResponsetDto> phoneCheck(
			@RequestBody @Valid PhoneCheckRequestDto requestBody){
		ResponseEntity<? super PhoneCheckResponsetDto> response = userService.phoneCheck(requestBody);
		return response;
	}
	
	@PostMapping("/email-certificaion")
	public ResponseEntity<? super EmailCertificaionResponseDto> emailCertificaion(
			@RequestBody @Valid EmailCertificaionRequsetDto requsetBody){
		ResponseEntity<? super EmailCertificaionResponseDto> response = userService.emailCertification(requsetBody);
		return response;
	}
	
	@PostMapping("/check-certificaion")
	public ResponseEntity<? super CheckCertificaionResponseDto> checkCertificaio(
			@RequestBody @Valid CheckCertificaionRequestDto requsetBody){
		ResponseEntity<? super CheckCertificaionResponseDto> response = userService.checkCertificaion(requsetBody);
		return response;
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<? super SignUpResponseDto> signUp(
			@RequestBody @Valid SignUpRequestDto requestBody){
		ResponseEntity<? super SignUpResponseDto> response = userService.signUp(requestBody);
		return response;	
	}
	
	@PostMapping("/sign-in")
	public ResponseEntity<? super SignInResponseDto> signIn(
			@RequestBody SignInRequestDto responsebody, HttpServletResponse sevletResponse){
		ResponseEntity<? super SignInResponseDto> response = userService.signIn(responsebody,sevletResponse);
		return response;
	}
	
	@PostMapping("/find-id")
	public ResponseEntity<? super FindIdResponseDto> findId(
			@RequestBody FindIdRequestDto responsebody){
		ResponseEntity<? super FindIdResponseDto> response = userService.findId(responsebody);
		return response;
	} 
	
	@PostMapping("/find-password")
	public ResponseEntity<? super FindPasswordResponseDto> findPassword(
			@RequestBody FindPasswordRequestDto responsebody){
		ResponseEntity<? super FindPasswordResponseDto> response = userService.findPassword(responsebody);
		return response;
	}
	
	@PostMapping("/password-certification")
	public ResponseEntity<? super CheckCertificaionResponseDto> passwordCertification(
			@RequestBody @Valid CheckCertificaionRequestDto requsetBody){
		ResponseEntity<? super CheckCertificaionResponseDto> response = userService.passwordCertification(requsetBody);
		return response;
	}
	
	@PostMapping("/social-sign-up")
	public ResponseEntity<? super SocialSignUpResponseDto> socialSignUp(
			@RequestBody @Valid SocialSignUpRequestDto requestBody){
		ResponseEntity<? super SocialSignUpResponseDto> response = userSocialService.socialSignUp(requestBody);
		return response;	
	}

//	@GetMapping("/check-cookie")
//	public ResponseEntity<? super CheckCookieResponseDto> checkAuth(@CookieValue(value = "authToken", required = false) String authToken) {
//		log.info("authtoken{}",authToken);
//		ResponseEntity<? super CheckCookieResponseDto> response = userService.checkAuth(authToken);
//	    return response;
//	}
	@GetMapping("/check-cookie")
	public ResponseEntity<? super CheckCookieResponseDto> checkAuth(
	    @RequestHeader(value = "Cookie", required = false) String cookieHeader
	) {
		log.info("cookieGeader{}",cookieHeader);
	    if (cookieHeader != null && cookieHeader.contains("authToken")) {
	        String authToken = Arrays.stream(cookieHeader.split(";"))
	            .map(String::trim)
	            .filter(cookie -> cookie.startsWith("authToken="))
	            .map(cookie -> cookie.substring("authToken=".length()))
	            .findFirst()
	            .orElse(null);

	        log.info("authToken from Cookie: {}", authToken);
	        return userService.checkAuth(authToken);
	    } else {
	        log.warn("No authToken found in cookies");
	        return CheckCookieResponseDto.unauthorized();
	    }
	}
	
	
}
