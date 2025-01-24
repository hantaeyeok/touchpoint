package com.touchpoint.kh.user.model.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SocialSignUpRequestDto {

    @NotBlank
    private String userId; // 사용자 ID

    @NotBlank
    private String username; // 사용자 이름

    @NotBlank
    private String phone; // 전화번호

    @Email
    @NotBlank
    private String email; // 이메일

    @NotBlank
    private String adAgreed; // 광고 동의 여부
}