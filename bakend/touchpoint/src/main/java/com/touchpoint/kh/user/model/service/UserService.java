package com.touchpoint.kh.user.model.service;

import org.springframework.http.ResponseEntity;

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
import com.touchpoint.kh.user.model.dto.response.SignInResponseDto;
import com.touchpoint.kh.user.model.dto.response.SignUpResponseDto;
import com.touchpoint.kh.user.model.dto.response.check.CheckCertificaionResponseDto;
import com.touchpoint.kh.user.model.dto.response.check.EmailCheckResponseDto;
import com.touchpoint.kh.user.model.dto.response.check.IdCheckResponseDto;
import com.touchpoint.kh.user.model.dto.response.check.PhoneCheckResponsetDto;
import com.touchpoint.kh.user.model.dto.response.find.FindIdResponseDto;
import com.touchpoint.kh.user.model.dto.response.find.FindPasswordResponseDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface UserService {
	
	ResponseEntity<? super IdCheckResponseDto> idCheck(IdCheckRequestDto dto);
	ResponseEntity<? super PhoneCheckResponsetDto> phoneCheck(PhoneCheckRequestDto dto);
	ResponseEntity<? super EmailCheckResponseDto> emailCheck(EmailCheckRequestDto dto);
	ResponseEntity<? super EmailCertificaionResponseDto> emailCertification(EmailCertificaionRequsetDto dto);
	ResponseEntity<? super CheckCertificaionResponseDto> checkCertificaion(CheckCertificaionRequestDto dto);
	ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
	ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto, HttpServletResponse response);
	ResponseEntity<? super FindIdResponseDto> findId(FindIdRequestDto dto);
	ResponseEntity<? super FindPasswordResponseDto> findPassword(FindPasswordRequestDto dto);
	ResponseEntity<? super CheckCertificaionResponseDto> passwordCertification(CheckCertificaionRequestDto dto);
	ResponseEntity<? super CheckCookieResponseDto> checkAuth(String authToken);
}
