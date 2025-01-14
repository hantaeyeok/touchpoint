package com.touchpoint.kh.user.model.vo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class LoginRequest {

	private String userIdOrPhone;
	private String userType;
	private String password;
	private String captchaToken;
	private String remember;
	
} 