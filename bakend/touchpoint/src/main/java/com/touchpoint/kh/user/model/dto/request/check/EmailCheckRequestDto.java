package com.touchpoint.kh.user.model.dto.request.check;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmailCheckRequestDto {

	@NotBlank
	@Email
	private String email;
}
