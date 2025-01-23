package com.touchpoint.kh.user.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "USER_SOCIAL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserSocial {

    @Id
    @Column(name = "PROVIDER_USER_ID", nullable = false, length = 50)
    private String providerUserId; // 소셜 제공자가 발급한 사용자 고유 ID (기본 키)

    @Column(name = "PROVIDER", nullable = false, length = 20)
    private String provider; // 소셜 로그인 제공자 (예: Kakao, Google)

    @Column(name = "USER_ID", nullable = false, length = 20)
    private String userId; // 애플리케이션 내부 사용자 ID
}	