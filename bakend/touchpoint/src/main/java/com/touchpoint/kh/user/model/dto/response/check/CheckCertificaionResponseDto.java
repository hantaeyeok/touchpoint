package com.touchpoint.kh.user.model.dto.response.check;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.touchpoint.kh.user.model.dto.response.ResponseDto;
import com.touchpoint.kh.user.model.vo.User;

import lombok.Getter;

@Getter
public class CheckCertificaionResponseDto extends ResponseDto{
	
	private User user;
	
	private CheckCertificaionResponseDto() {
		super();	
	}
	
	private CheckCertificaionResponseDto(User user) {
		this.user = user;
	} 
	
	public static ResponseEntity<CheckCertificaionResponseDto> success(User user){
		CheckCertificaionResponseDto responsebody = new CheckCertificaionResponseDto(user);
		return ResponseEntity.status(HttpStatus.OK).body(responsebody);
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
