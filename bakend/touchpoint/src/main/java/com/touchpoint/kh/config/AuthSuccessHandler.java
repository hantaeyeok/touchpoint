package com.touchpoint.kh.config;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.touchpoint.kh.user.model.dao.UserSessionRepository;
import com.touchpoint.kh.user.model.vo.UserSession;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    private final UserSessionRepository userSessionRepository;
    private final SessionRegistry sessionRegistry;
  
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String userId = authentication.getName();
        deactivateSession(userId);
        createSession(userId, request.getSession().getId(), authentication.getPrincipal());
        response.sendRedirect("/");        // 로그인 성공 후 리다이렉트
        
        //authentication.getPrincipal()
        //pring Security의 Authentication 객체에서 호출되는 메서드로, 현재 인증된 사용자의 주요 정보를 반환합니다.
        //String: 사용자의 이름(기본적으로 사용자 ID 또는 사용자명).
        //UserDetails: Spring Security에서 제공하는 사용자 상세 정보 객체.
        //Custom 객체: 사용자 정의 Principal 객체.
        //null: 인증되지 않은 경우.

    }
    
    private void deactivateSession(String userId) {
    	// 기존 활성 세션 비활성화 ifPresent Optional 값이 있으면 true 없으면 false 리턴
        userSessionRepository.findByUserIdAndSessionStatus(userId, "A").ifPresent(session -> {
            session.setSessionStatus("I"); // 기존 세션 비활성화
            userSessionRepository.save(session);
        });
    }
    
    private void createSession(String userId, String sessionId, Object principal) {
        // 새로운 활성 세션 생성 및 저장

        // 세션 정보를 DB에 저장
        UserSession userSession = UserSession.builder()
                .userId(userId)
                .sessionStatus("A")
                .loginTime(LocalDateTime.now())
                .build();
        userSessionRepository.save(userSession);

        // 세션 정보를 Spring Security SessionRegistry에 등록
        sessionRegistry.registerNewSession(sessionId, principal);
    }
}

	
	


