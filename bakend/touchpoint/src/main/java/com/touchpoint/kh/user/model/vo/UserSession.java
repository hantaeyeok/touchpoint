package com.touchpoint.kh.user.model.vo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "USER_SESSION")
public class UserSession {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USER_SESSION_SEQ_GEN")
    @SequenceGenerator(name = "USER_SESSION_SEQ_GEN", sequenceName = "USER_SESSION_SEQ", allocationSize = 1)
    @Column(name = "SESSION_ID")
    private Long sessionId;

    @Column(name = "USER_ID", length = 20, nullable = false)
    private String userId;

    @Column(name = "SESSION_STATUS", length = 1, nullable = false)
    private String sessionStatus; // A: 활성, I: 비활성

    @Column(name = "LOGIN_TIME", nullable = false)
    private LocalDateTime loginTime;
}