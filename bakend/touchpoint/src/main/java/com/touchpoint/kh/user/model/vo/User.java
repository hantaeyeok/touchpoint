package com.touchpoint.kh.user.model.vo;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "USER")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "USER_CD")
	private int userCd;
	
	@Column(name = "USER_ID", nullable = false, unique = true) // USER_ID : UNIQUE, NOT NULL
    private String userName;
	
	@Column(name = "EMAIL", nullable = false) // EMAIL : NOT NULL
    private String email;
	
	@Column(name = "PHONE_NO", unique = true) // PHONE_NO : UNIQUE
    private String phoneNo;

    @Column(name = "PASSWORD", nullable = false) // PASSWORD : NOT NULL
    private String password;

    @Column(name = "NAME", nullable = false) // NAME : NOT NULL
    private String name;

    @Column(name = "JOIN_DT", nullable = false) // JOIN_DT 기본값 SYSDATE
    private LocalDate joinDate = LocalDate.now();

    @Column(name = "LAST_LOGIN_DT") // LAST_LOGIN_DT :  NULL 허용
    private LocalDate lastLoginDate;

    @Column(name = "USER_ST", nullable = false) // USER_ST :  기본값 'Y'
    private String userStatus = "Y";

    @Column(name = "INFO_COMPLETE", nullable = true) // INFO_COMPLETE :  기본값 'Y'
    private String infoComplete = "Y";

    @Column(name = "LOGIN_TYPE", nullable = false) // LOGIN_TYPE :  기본값 'LOCAL'
    private String loginType = "LOCAL";

    @Column(name = "USER_TYPE", nullable = false) // USER_TYPE :  기본값 'USER'
    private String userType = "USER";
}
