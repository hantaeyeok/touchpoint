package com.touchpoint.kh.user.model.service.oath2;


import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.touchpoint.kh.user.model.dao.UserSessionRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthLogoutHandler implements LogoutSuccessHandler {

    private final UserSessionRepository userSessionRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException {
        if (authentication != null) {
            String userId = authentication.getName();

            // 활성 세션 비활성화
            userSessionRepository.findByUserIdAndSessionStatus(userId, "A")
                .ifPresent(session -> {
                    session.setSessionStatus("I"); // 세션 비활성화
                    userSessionRepository.save(session);
                });
        }

        // 로그아웃 성공 후 리다이렉트
        response.sendRedirect("/login?logout=true");
    }
}
