package com.touchpoint.kh.user.model.vo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "LOGIN_ATTEMPT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginAttempt {

	@Id
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOGIN_ATTEMPT_SEQ_GEN")
    @SequenceGenerator(name = "LOGIN_ATTEMPT_SEQ_GEN", sequenceName = "LOGIN_ATTEMPT_SEQ", allocationSize = 1)
    @Column(name = "ID")
    private Long id; // 기본 키

    @Column(name = "USER_ID", nullable = false, length = 20)
    private String userId; // 사용자 ID

    @Column(name = "FAILED_LOGIN_CNT", nullable = false)
    private int failedLoginCnt; // 로그인 실패 횟수

    @Column(name = "CAPTCHA_ACTIVE", nullable = false, length = 1, columnDefinition = "CHAR(1) DEFAULT 'N'")
    private String captchaActive; // 캡차 활성화 여부 ('Y' 또는 'N')

    @Column(name = "CAPTCHA_TIMER")
    private LocalDateTime captchaTimer; // 캡차 제한 시간// 다시 삭제해야함
}
