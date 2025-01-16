package com.touchpoint.kh.user.model.dto.response.check;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.touchpoint.kh.user.common.ResponseCode;
import com.touchpoint.kh.user.common.ResponseMessage;
import com.touchpoint.kh.user.model.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class EmailCheckResponseDto extends ResponseDto {

		private EmailCheckResponseDto() {
			super();
		}

		public static ResponseEntity<EmailCheckResponseDto> success(){
			EmailCheckResponseDto responseBody = new EmailCheckResponseDto();
			return ResponseEntity.status(HttpStatus.OK).body(responseBody);
		}
		
		public static ResponseEntity<ResponseDto> duplicatEmail(){
			ResponseDto responseBody = new ResponseDto(ResponseCode.DUPLICATE_EMAIL, ResponseMessage.DUPLICATE_EMAIL);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
		} 
		
}
