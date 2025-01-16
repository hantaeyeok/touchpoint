package com.touchpoint.kh.user.model.vo;

import java.time.LocalDate;

import com.touchpoint.kh.user.model.dto.response.SignUpRequestDto;

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

    @Column(name = "USER_ID", length = 20)
    private String userId; // 사용자 ID

    @Column(name = "PASSWORD", length = 255)
    private String password; // 암호화된 비밀번호

    @Column(name = "EMAIL", length = 50)
    private String email; // 이메일

    @Column(name = "PHONE_NO", length = 15)
    private String phoneNo; // 전화번호 (VARCHAR2(15)와 매핑)

    @Column(name = "NAME", length = 50)
    private String name; // 사용자 이름

    @Column(name = "JOIN_DT", nullable = false)
    private LocalDate joinDt; // 가입 날짜 (SQL의 DATE와 매핑)

    @Column(name = "USER_ST", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'Y' CHECK (USER_ST IN ('Y', 'N'))")
    private String userSt; // 계정 상태 (Y: 활성, N: 비활성)

    @Column(name = "SOCIAL_USER", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N' CHECK (SOCIAL_USER IN ('Y', 'N'))")
    private String socialUser; // 소셜 로그인 여부 (Y/N)

    @Column(name = "AD_AGREED", length = 1, nullable = false, columnDefinition = "CHAR(1) DEFAULT 'N' CHECK (AD_AGREED IN ('Y', 'N'))")
    private String adAgreed; // 광고 동의 여부 (Y/N)
    
    @Column(name = "USER_ROLE", length = 20, nullable = false, columnDefinition = "VARCHAR(20) DEFAULT 'ROLE_USER'")
    private String userRole; // 사용자 역할 (기본값: ROLE_USER)
    
    public User(SignUpRequestDto dto) {
    	this.userId = dto.getId();
    	this.password = dto.getPassword();
    	this.email = dto.getEmail();
    	//this.type = "app";
    	this.userRole = "ROLE_USER";
    	
    	
    }
    
}
