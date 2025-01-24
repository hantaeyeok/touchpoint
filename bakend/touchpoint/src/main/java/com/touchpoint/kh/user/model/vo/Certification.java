package com.touchpoint.kh.user.model.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "CERTIFICATION")
@Table(name = "CERTIFICATION")
public class Certification {

    @Id
    @Column(name = "user_id", length = 30, nullable = false)
    private String userId;

    @Column(name = "email", length = 255, nullable = false)
    private String email;

    @Column(name = "certificaion_number", length = 6, nullable = false)
    private String certificationNumber;
}