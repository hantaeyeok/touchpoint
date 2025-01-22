package com.touchpoint.kh.user.model.dto.response.find;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.touchpoint.kh.user.common.ResponseCode;
import com.touchpoint.kh.user.common.ResponseMessage;
import com.touchpoint.kh.user.model.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class FindPasswordResponseDto extends ResponseDto {

	private FindPasswordResponseDto() {
	        super();
	    }

    // 비밀번호 초기화 성공
    public static ResponseEntity<FindPasswordResponseDto> resetSuccess() {
        FindPasswordResponseDto responseBody = new FindPasswordResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    // 비밀번호 초기화 실패
    public static ResponseEntity<ResponseDto> resetFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.PASSWORD_RESET_FAIL, ResponseMessage.PASSWORD_RESET_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

    // 이메일 인증 실패
    public static ResponseEntity<ResponseDto> certificationFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.CERTIFICATION_FAIL, ResponseMessage.CERTIFICATION_FAIL);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
    }
}
