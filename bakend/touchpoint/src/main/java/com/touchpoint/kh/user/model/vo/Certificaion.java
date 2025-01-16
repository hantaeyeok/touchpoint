package com.touchpoint.kh.user.model.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CERTIFICAION")
@Table(name = "CERTIFICAION")
public class Certificaion {
	
	@Id
	private String userId;
	private String email;
	private String certificationNumber;
}
