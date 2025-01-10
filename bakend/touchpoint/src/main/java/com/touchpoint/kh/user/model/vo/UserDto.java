package com.touchpoint.kh.user.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {

    private String userId;
    private String password;
    private String email;
    private String name;
    private String phone;
    private boolean adAgreed; // 광고 동의 여부
}
