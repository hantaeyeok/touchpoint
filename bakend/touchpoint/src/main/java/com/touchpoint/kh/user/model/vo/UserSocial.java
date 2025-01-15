package com.touchpoint.kh.user.model.vo;

import java.time.LocalDate;

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
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Table(name = "USERS_SOCIAL")
public class UserSocial {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_social_seq_generator")
    @SequenceGenerator(name = "users_social_seq_generator", sequenceName = "USERS_SOCIAL_SEQ", allocationSize = 1)
    @Column(name = "SOCIAL_CD", nullable = false)
    private Long socialCd; // 고유 식별자 (Primary Key)

    @Column(name = "USER_CD", nullable = false)
    private Long userCd; // USER 테이블의 고유 식별자 (Foreign Key)

    @Column(name = "PROVIDER", length = 20, nullable = false)
    private String provider; // 소셜 로그인 제공자 (예: KAKAO, GOOGLE, NAVER)

    @Column(name = "PROVIDER_USER_ID", length = 50, nullable = false)
    private String providerUserId; // 소셜 제공자의 고유 사용자 ID

    @Column(name = "ACCESS_TOKEN", length = 255, nullable = false)
    private String accessToken; // 소셜 로그인 인증을 위한 액세스 토큰

    @Column(name = "REFRESH_TOKEN", length = 255)
    private String refreshToken; // 액세스 토큰 갱신을 위한 리프레시 토큰 (선택적)

    @Column(name = "TOKEN_EXPIRY", nullable = false)
    private LocalDate tokenExpiry; // 액세스 토큰의 만료 일자
}
