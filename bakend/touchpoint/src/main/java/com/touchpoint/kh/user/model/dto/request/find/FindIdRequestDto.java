package com.touchpoint.kh.user.model.dto.request.find;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FindIdRequestDto {

	@NotBlank
	private String userName;
	
	@Email
	private String email;
	
	private String phone;
}
