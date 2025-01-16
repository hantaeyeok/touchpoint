package com.touchpoint.kh.user.model.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.touchpoint.kh.user.common.ResponseCode;
import com.touchpoint.kh.user.common.ResponseMessage;

import lombok.Getter;

@Getter
public class SignUpResponseDto extends ResponseDto {

	private SignUpResponseDto() {
		super();
	}
	
	public static ResponseEntity<SignUpResponseDto> success(){
		SignUpResponseDto responseBody = new SignUpResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(responseBody);
	}
	
	public static ResponseEntity<ResponseDto> duplicateId(){
		ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_ID, ResponseMessage.DUPLICATE_ID);;
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}
	
	public static ResponseEntity<ResponseDto> certificaionFail(){
		ResponseDto responseBody = new ResponseDto(ResponseCode.CERTIFICATION_FALI, ResponseMessage.CERTIFICATION_FALI);;
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
	}
}
