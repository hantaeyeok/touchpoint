package com.touchpoint.kh.user.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * MyBatis 전용 LoginAttempt 클래스
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class LoginAttemptMy {

    private Long attemptId;        // 시도 ID
    private String userId;         // 사용자 ID
    private int failedLoginCnt;    // 로그인 실패 횟수
    private String captchaActive;  // 캡차 활성화 여부 (Y/N)

}
