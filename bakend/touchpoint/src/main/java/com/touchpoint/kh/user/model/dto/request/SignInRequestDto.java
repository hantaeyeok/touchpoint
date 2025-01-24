package com.touchpoint.kh.user.model.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignInRequestDto {

	@NotBlank
	private String userIdOrPhone;
	
	@NotBlank
	private String password;
	
	
	private String captchaToken;
}
