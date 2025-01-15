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
    @Column(name = "ATTEMPT_ID", nullable = false)
    private Long attemptId; // 기본 키

    @Column(name = "USER_ID", nullable = false, length = 20)
    private String userId; // 사용자 ID

    @Builder.Default
    @Column(name = "FAILED_LOGIN_CNT", nullable = false)
    private int failedLoginCnt = 0; // 로그인 실패 횟수, 기본값 0

    @Builder.Default
    @Column(name = "CAPTCHA_ACTIVE", nullable = false, length = 1)
    private String captchaActive = "N"; // 캡차 활성화 여부, 기본값 'N'
}
