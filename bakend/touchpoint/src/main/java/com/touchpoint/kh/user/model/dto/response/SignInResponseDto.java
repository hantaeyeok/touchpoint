package com.touchpoint.kh.user.model.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.touchpoint.kh.user.common.ResponseCode;
import com.touchpoint.kh.user.common.ResponseMessage;

import lombok.Getter;

@Getter
public class SignInResponseDto extends ResponseDto{

	private String token;
	private int expirationTime;
	
	
	private SignInResponseDto(String token) {
		super();
		this.token = token;
		this.expirationTime = 3600;
	}
	
	public static ResponseEntity<SignInResponseDto> success (String token){
		SignInResponseDto responsBody = new SignInResponseDto(token);
		return ResponseEntity.status(HttpStatus.OK).body(responsBody);
	}
	
	public static ResponseEntity<ResponseDto> signInFail (){
		ResponseDto responsBody = new ResponseDto(ResponseCode.SIGN_IN_FAIL,ResponseMessage.SIGN_IN_FAIL);
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responsBody);
	}
	
}
