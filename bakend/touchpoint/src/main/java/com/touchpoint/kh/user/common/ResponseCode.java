package com.touchpoint.kh.user.common;

public interface ResponseCode {

    String SUCCESS = "SU"; // 성공
    String VALIDATION_FAIL = "VF"; // 검증 실패
    String DUPLICATE_ID = "DI"; // 중복된 아이디
    String DUPLICATE_EMAIL = "DE"; // 중복된 이메일
    String DUPLICATE_PHONE = "DP"; // 중복된 전화번호
    String SIGN_IN_FAIL = "SF"; // 로그인 정보 불일치
    
    String CERTIFICATION_FAIL = "CF"; // 인증 실패
    
    String MAIL_FAIL = "MF"; // 메일 전송 실패
    String DATABASE_ERROR = "DBE"; // 데이터베이스 오류
    
    // 로그인 관련
    String ACCOUNT_LOCKED = "AL"; // 계정 잠김
    String CAPTCHA_FAIL = "CF"; // 캡차 인증 실패
    String PASSWORD_FAIL_AFTER_CAPTCHA = "PFAC"; // 캡차 인증 성공 후 비밀번호 불일치
    
    // 아이디 및 비밀번호 찾기 관련
    String ID_FIND_SUCCESS = "IFS"; // 아이디 찾기 성공
    String ID_FIND_FAIL = "IFF"; // 아이디 찾기 실패
    String PASSWORD_RESET_SUCCESS = "PRS"; // 비밀번호 초기화 성공
    String PASSWORD_RESET_FAIL = "PRF"; // 비밀번호 초기화 실패
}
