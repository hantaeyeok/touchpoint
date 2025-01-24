package com.touchpoint.kh.user.model.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.touchpoint.kh.user.common.ResponseCode;
import com.touchpoint.kh.user.common.ResponseMessage;

public class SocialSignUpResponseDto extends ResponseDto {

	private SocialSignUpResponseDto() {
		super();
	}
	
	public static ResponseEntity<SocialSignUpResponseDto> success(){
		SocialSignUpResponseDto responseBody = new SocialSignUpResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(responseBody);
	}
	
	 public static ResponseEntity<ResponseDto> socialAuthFail() {
	        ResponseDto responseBody = new ResponseDto(ResponseCode.SOCIAL_AUTH_FAIL, ResponseMessage.SOCIAL_AUTH_FAIL);
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
	  }
	
	public static ResponseEntity<ResponseDto> certificaionFail(){
		ResponseDto responseBody = new ResponseDto(ResponseCode.CERTIFICATION_FAIL, ResponseMessage.CERTIFICATION_FAIL);;
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
	}
}
