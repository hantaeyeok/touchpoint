package com.touchpoint.kh.user.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailCertificaionRequsetDto {

	@NotBlank
	private String userId;
	
	@Email
	@NotBlank
	private String email;
}
