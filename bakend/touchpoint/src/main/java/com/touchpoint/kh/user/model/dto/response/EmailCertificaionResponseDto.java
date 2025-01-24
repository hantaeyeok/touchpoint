package com.touchpoint.kh.user.model.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.touchpoint.kh.user.common.ResponseCode;
import com.touchpoint.kh.user.common.ResponseMessage;

import lombok.Getter;

@Getter
public class EmailCertificaionResponseDto extends ResponseDto{

	private EmailCertificaionResponseDto() {
		super();
	}
	
	public static ResponseEntity<EmailCertificaionResponseDto> success(){
		EmailCertificaionResponseDto responseBody = new EmailCertificaionResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(responseBody);
	}
	
	public static ResponseEntity<ResponseDto> dublicateId(){
		ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_ID, ResponseMessage.DUPLICATE_ID);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	}
	
	public static ResponseEntity<ResponseDto> mailSendFail(){
		ResponseDto responseBody = new ResponseDto(ResponseCode.MAIL_FAIL, ResponseMessage.MAIL_FAIL);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseBody);
	}
}
