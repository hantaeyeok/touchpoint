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
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_generator")
    @SequenceGenerator(name = "user_seq_generator", sequenceName = "USER_SEQ", allocationSize = 1)
    @Column(name = "USER_CD", nullable = false)
    private Long userCd; // 사용자 고유 코드 (Primary Key)

    @Column(name = "USER_ID", length = 20, nullable = true)
    private String userId; // 사용자 ID

    @Column(name = "PASSWORD", length = 255, nullable = true)
    private String password; // 암호화된 비밀번호

    @Column(name = "EMAIL", length = 50, nullable = true)
    private String email; // 이메일

    @Column(name = "PHONE_NO", nullable = true)
    private String phoneNo; // 전화번호

    @Column(name = "NAME", length = 50, nullable = true)
    private String name; // 사용자 이름

    @Column(name = "JOIN_DT", nullable = false)
    private LocalDate joinDate; // 가입 날짜

    @Column(name = "USER_ST", length = 1, nullable = false)
    private String userStatus; // 계정 상태 (Y: 활성, N: 비활성)

    @Column(name = "SOCIAL_USER", length = 1, nullable = false)
    private String socialUser; // 소셜 로그인 여부 (Y/N)
    
    @Column(name = "AD_AGREED", length = 1, nullable = false)
    private String adAgreed;
    
}

