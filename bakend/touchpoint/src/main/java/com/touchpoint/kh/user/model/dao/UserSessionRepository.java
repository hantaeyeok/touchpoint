package com.touchpoint.kh.user.model.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.touchpoint.kh.user.model.vo.UserSession;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

    // 특정 사용자 ID로 활성화된 세션 검색
    Optional<UserSession> findByUserIdAndSessionStatus(String userId, String sessionStatus);
}