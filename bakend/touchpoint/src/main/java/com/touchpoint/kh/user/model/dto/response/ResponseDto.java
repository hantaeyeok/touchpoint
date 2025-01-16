package com.touchpoint.kh.user.model.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.touchpoint.kh.user.common.ResponseCode;
import com.touchpoint.kh.user.common.ResponseMessage;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto {

	private String code;
	private String message;
	
	public ResponseDto() {
		this.code = ResponseCode.SUCCESS;
		this.message = ResponseMessage.SUCCESS;
	}
	
	public static ResponseEntity<ResponseDto> databaseError(){
		ResponseDto responseBody = new ResponseDto(ResponseCode.DATABASE_ERROR, ResponseMessage.DATABASE_ERROR);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
	}
	
	public static ResponseEntity<ResponseDto> validaionFail(){
		ResponseDto responseBody = new ResponseDto(ResponseCode.VALIDATION_FAIL, ResponseMessage.VALIDATION_FAIL);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}

}
