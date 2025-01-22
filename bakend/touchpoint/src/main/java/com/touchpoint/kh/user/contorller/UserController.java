package com.touchpoint.kh.user.contorller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
import com.touchpoint.kh.user.model.dto.response.SignInResponseDto;
import com.touchpoint.kh.user.model.dto.response.SignUpResponseDto;
import com.touchpoint.kh.user.model.dto.response.check.CheckCertificaionResponseDto;
import com.touchpoint.kh.user.model.dto.response.check.EmailCheckResponseDto;
import com.touchpoint.kh.user.model.dto.response.check.IdCheckResponseDto;
import com.touchpoint.kh.user.model.dto.response.check.PhoneCheckResponsetDto;
import com.touchpoint.kh.user.model.dto.response.find.FindIdResponseDto;
import com.touchpoint.kh.user.model.dto.response.find.FindPasswordResponseDto;
import com.touchpoint.kh.user.model.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	
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
	} //Su success일때 다음검증이동
	
	@PostMapping("/sign-up")
	public ResponseEntity<? super SignUpResponseDto> signUp(
			@RequestBody @Valid SignUpRequestDto requestBody){
		ResponseEntity<? super SignUpResponseDto> response = userService.signUp(requestBody);
		return response;
		
	}
	
	@PostMapping("/sign-in")
	public ResponseEntity<? super SignInResponseDto> signIn(
			@RequestBody SignInRequestDto responsebody){
		ResponseEntity<? super SignInResponseDto> response = userService.signIn(responsebody);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
