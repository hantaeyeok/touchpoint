package com.touchpoint.kh.user.model.dto.response.check;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.touchpoint.kh.user.common.ResponseCode;
import com.touchpoint.kh.user.common.ResponseMessage;
import com.touchpoint.kh.user.model.dto.response.ResponseDto;

public class PhoneCheckResponsetDto extends ResponseDto{
	
	private PhoneCheckResponsetDto() {
		super();
	}
	
	public static ResponseEntity<PhoneCheckResponsetDto> success(){
		PhoneCheckResponsetDto responseBody = new PhoneCheckResponsetDto();
		return ResponseEntity.status(HttpStatus.OK).body(responseBody);
	}
	
	
	public static ResponseEntity<ResponseDto> duplicatPhone(){
		ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_PHONE, ResponseMessage.DUPLICATE_PHONE);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	} 
}
