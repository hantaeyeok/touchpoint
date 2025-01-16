package com.touchpoint.kh.user.model.service;

import org.springframework.http.ResponseEntity;

import com.touchpoint.kh.common.ResponseData;
import com.touchpoint.kh.user.model.dto.request.CheckCertificaionRequestDto;
import com.touchpoint.kh.user.model.dto.request.EmailCertificaionRequsetDto;
import com.touchpoint.kh.user.model.dto.request.IdCheckRequestDto;
import com.touchpoint.kh.user.model.dto.request.SignInRequestDto;
import com.touchpoint.kh.user.model.dto.response.CheckCertificaionResponseDto;
import com.touchpoint.kh.user.model.dto.response.EmailCertificaionResponseDto;
import com.touchpoint.kh.user.model.dto.response.IdCheckResponseDto;
import com.touchpoint.kh.user.model.dto.response.SignInResponseDto;
import com.touchpoint.kh.user.model.dto.response.SignUpRequestDto;
import com.touchpoint.kh.user.model.dto.response.SignUpResponseDto;
import com.touchpoint.kh.user.model.vo.LoginRequest;
import com.touchpoint.kh.user.model.vo.User;
import com.touchpoint.kh.user.model.vo.UserDto;

public interface UserService {
	
	
	ResponseEntity<? super IdCheckResponseDto> idcheck(IdCheckRequestDto dto);
	ResponseEntity<? super EmailCertificaionResponseDto> emailCertification(EmailCertificaionRequsetDto dto);
	ResponseEntity<? super CheckCertificaionResponseDto> checkCertificaion(CheckCertificaionRequestDto dto);
	ResponseEntity<? super SignUpResponseDto> signUp(SignUpRequestDto dto);
	ResponseEntity<? super SignInResponseDto> signIn(SignInRequestDto dto);
	//user id checked
	Boolean userIdChecked(String userId);
	
	//user email checked
	Boolean userEmailChecked(String email);
	
	Boolean userPhoneChecked(String phone);
	
	//user signupGeneralUser
	User signupGeneralUser(UserDto userDto);
	
	//GeneralUser-login
	ResponseData validateLogin(LoginRequest loginRequest);
	
	
}
