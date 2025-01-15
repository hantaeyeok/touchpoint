package com.touchpoint.kh.user.model.service.oath2;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.touchpoint.kh.user.model.dao.UserSessionRepository;
import com.touchpoint.kh.user.model.vo.UserSession;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final UserSessionRepository userSessionRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String userId = authentication.getName();

        // 기존 활성 세션 비활성화
        userSessionRepository.findByUserIdAndSessionStatus(userId, "A")
            .ifPresent(session -> {
                session.setSessionStatus("I");
                userSessionRepository.save(session);
            });

        // 새로운 세션 생성
        UserSession newSession = UserSession.builder()
            .userId(userId)
            .sessionStatus("A")
            .loginTime(LocalDateTime.now())
            .build();
        userSessionRepository.save(newSession);

        response.sendRedirect("/");
    }
}
