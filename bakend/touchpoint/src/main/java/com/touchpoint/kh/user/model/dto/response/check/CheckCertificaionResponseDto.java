package com.touchpoint.kh.user.model.dto.response.check;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.touchpoint.kh.user.model.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class CheckCertificaionResponseDto extends ResponseDto{
	
	private CheckCertificaionResponseDto() {
		super();
		
	}
	
	public static ResponseEntity<CheckCertificaionResponseDto> success(){
		CheckCertificaionResponseDto responsebody = new CheckCertificaionResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(responsebody);
	}
	
	public static ResponseEntity<ResponseDto> certificaionFail(){
		ResponseDto responsebody = new ResponseDto();
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responsebody);
	}
	
	
	
}
