package com.touchpoint.kh.user.model.dto.response.find;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.touchpoint.kh.user.common.ResponseCode;
import com.touchpoint.kh.user.common.ResponseMessage;
import com.touchpoint.kh.user.model.dto.response.ResponseDto;

import lombok.Getter;

@Getter
public class FindIdResponseDto extends ResponseDto{
	
	private String userId;
	 
	private FindIdResponseDto() {
	        super();
	    }

	private FindIdResponseDto(String userId) {
		this.userId = userId;
	}
	
	 // ID 찾기 성공
    public static ResponseEntity<FindIdResponseDto> success(String userId) {
        FindIdResponseDto responseBody = new FindIdResponseDto(userId);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    
    // ID 찾기 실패
    public static ResponseEntity<ResponseDto> findFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.ID_FIND_FAIL, ResponseMessage.ID_FIND_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
    
    //소셜 로그인 반려
    public static ResponseEntity<ResponseDto> findSocialFail() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.FIND_SOCIAL_FAIL, ResponseMessage.FIND_SOCIAL_FAIL);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }
}
