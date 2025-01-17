package com.touchpoint.kh.user.model.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.touchpoint.kh.user.common.ResponseCode;
import com.touchpoint.kh.user.common.ResponseMessage;

import lombok.Getter;

@Getter
public class SignInResponseDto extends ResponseDto {
    private String token;
    private int expirationTime;
    private int loginFailCount;
    private String captchaActive;

    private SignInResponseDto(String token) {
        super();
        this.token = token;
        this.expirationTime = 3600;
    }

    private SignInResponseDto(String code, String message, int loginFailCount, String captchaActive) {
        super(code, message);
        this.loginFailCount = loginFailCount;
        this.captchaActive = captchaActive;
    }

    public static ResponseEntity<ResponseDto> validataSuccess() {
        ResponseDto responsBody = new ResponseDto();
        return ResponseEntity.status(HttpStatus.OK).body(responsBody);
    }

    public static ResponseEntity<SignInResponseDto> success(String token) {
        SignInResponseDto responsBody = new SignInResponseDto(token);
        return ResponseEntity.status(HttpStatus.OK).body(responsBody);
    }

    public static ResponseEntity<ResponseDto> signInFail() {
        ResponseDto responsBody = new ResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responsBody);
    }

    public static ResponseEntity<SignInResponseDto> accountLocked(int loginFailCount, String captchaActive) {
        SignInResponseDto responseBody = new SignInResponseDto(ResponseCode.ACCOUNT_LOCKED, ResponseMessage.ACCOUNT_LOCKED, loginFailCount, captchaActive);
        return ResponseEntity.status(HttpStatus.LOCKED).body(responseBody);
    }

    public static ResponseEntity<SignInResponseDto> signInFail(int loginFailCount, String captchaActive) {
        SignInResponseDto responseBody = new SignInResponseDto(ResponseCode.SIGN_IN_FAIL, ResponseMessage.SIGN_IN_FAIL, loginFailCount, captchaActive);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseBody);
    }

    public static ResponseEntity<SignInResponseDto> captchaFail(int loginFailCount, String captchaActive) {
        SignInResponseDto responseBody = new SignInResponseDto(ResponseCode.CAPTCHA_FAIL, ResponseMessage.CAPTCHA_FAIL, loginFailCount, captchaActive);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(responseBody);
    }

    public static ResponseEntity<SignInResponseDto> validataSuccess(int loginFailCount, String captchaActive) {
        SignInResponseDto responseBody = new SignInResponseDto(ResponseCode.SUCCESS, ResponseMessage.SUCCESS, loginFailCount, captchaActive);
        return ResponseEntity.status(HttpStatus.OK).body(responseBody);
    }
}