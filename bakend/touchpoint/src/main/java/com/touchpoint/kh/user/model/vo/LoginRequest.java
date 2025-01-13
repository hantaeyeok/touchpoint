package com.touchpoint.kh.user.model.vo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
public class LoginRequest {

	private String userId;
	private String password;
	private String captchaToken;
	
}