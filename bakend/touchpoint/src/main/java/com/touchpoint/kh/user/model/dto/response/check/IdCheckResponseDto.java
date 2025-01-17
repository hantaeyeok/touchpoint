package com.touchpoint.kh.user.model.dto.response.check;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.touchpoint.kh.user.common.ResponseCode;
import com.touchpoint.kh.user.common.ResponseMessage;
import com.touchpoint.kh.user.model.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class IdCheckResponseDto extends ResponseDto{

	private IdCheckResponseDto() {
		super();
	}
	
	public static ResponseEntity<IdCheckResponseDto> success(){
		IdCheckResponseDto responseBody = new IdCheckResponseDto();
		return ResponseEntity.status(HttpStatus.OK).body(responseBody);
	}
	
	public static ResponseEntity<ResponseDto> duplicatId(){
		ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_ID, ResponseMessage.DUPLICATE_ID);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
	} 
}
