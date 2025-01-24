package com.touchpoint.kh.user.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor	
public class SignUpRequestDto {

	@NotBlank
	@Pattern(regexp="^[a-zA-Z0-9]{5,20}$")
	private String userId;
	
	@NotBlank
	@Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
	private String password;
	
	@Email
	@NotBlank
	private String email;
	
	@NotBlank
	private String certificationNumber;
	
	@NotBlank
	private String userName;
	
	@NotBlank
	private String phone;
	
	@NotBlank
	private String adAgreed;
	
	
}
