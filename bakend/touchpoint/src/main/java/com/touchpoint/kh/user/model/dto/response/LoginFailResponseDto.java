package com.touchpoint.kh.user.model.dto.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.Getter;

@Getter
public class LoginFailResponseDto extends ResponseDto {
    private int failedAttempts;
    private boolean captchaRequired;

    public LoginFailResponseDto(String code, String message, int failedAttempts, boolean captchaRequired) {
        super(code, message);
        this.failedAttempts = failedAttempts;
        this.captchaRequired = captchaRequired;
    }

    // 정적 메서드로 편리한 생성 지원
    public static ResponseEntity<LoginFailResponseDto> fail(int failedAttempts, boolean captchaRequired) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new LoginFailResponseDto("SF", "Login failed", failedAttempts, captchaRequired));
    }
}
