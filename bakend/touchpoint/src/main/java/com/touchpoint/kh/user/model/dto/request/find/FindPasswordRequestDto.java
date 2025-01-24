package com.touchpoint.kh.user.model.dto.request.find;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindPasswordRequestDto {

	@NotBlank
	private String userIdOrPhone;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String certificationNumber;
	
}
