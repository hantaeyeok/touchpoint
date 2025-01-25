package com.touchpoint.kh.user.model.dto.response;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.touchpoint.kh.user.common.ResponseCode;
import com.touchpoint.kh.user.common.ResponseMessage;

import lombok.Getter;

@Getter
public class CheckCookieResponseDto extends ResponseDto {

    private Map<String, String> userInfo;

    private CheckCookieResponseDto(Map<String, String> userInfo) {
        super();
        this.userInfo = userInfo;
    }

    // 성공 응답: 사용자 정보 포함
    public static ResponseEntity<CheckCookieResponseDto> success(Map<String, String> userInfo) {
        CheckCookieResponseDto responseBody = new CheckCookieResponseDto(userInfo);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }

    // 인증 실패 응답
    public static ResponseEntity<ResponseDto> unauthorized() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.USER_NOT_FOUND, ResponseMessage.UNAUTHORIZED);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }

    // 쿠키 없음 응답
    public static ResponseEntity<ResponseDto> noCookie() {
        ResponseDto responseBody = new ResponseDto(ResponseCode.NO_COOKIE, ResponseMessage.NO_COOKIE);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody);
    }

}