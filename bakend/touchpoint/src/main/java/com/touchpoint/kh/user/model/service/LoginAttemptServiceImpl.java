package com.touchpoint.kh.user.model.service;

import org.springframework.stereotype.Service;

import com.touchpoint.kh.user.model.dao.LoginAttemptRepository;
import com.touchpoint.kh.user.model.vo.LoginAttempt;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginAttemptServiceImpl implements LoginAttemptService {

    private final LoginAttemptRepository loginAttemptRepository;
    
    public LoginAttempt findLoginAttemptByUserId(String userId) {
        return loginAttemptRepository.findByUserId(userId);
    }

    // LoginAttempt 삽입
    @Transactional
    public LoginAttempt saveLoginAttempt(LoginAttempt loginAttempt) {
        return loginAttemptRepository.save(loginAttempt);
    }

    // LoginAttempt 삭제
    @Transactional
    public void deleteLoginAttemptByUserId(String userId) {
        loginAttemptRepository.deleteByUserId(userId);
    }

}
