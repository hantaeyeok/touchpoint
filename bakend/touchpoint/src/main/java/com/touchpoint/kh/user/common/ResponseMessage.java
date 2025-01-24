package com.touchpoint.kh.user.common;

public interface ResponseMessage {

    String SUCCESS = "Success"; // 성공
    String VALIDATION_FAIL = "Validation failed"; // 검증 실패
    String DUPLICATE_ID = "Duplicate Id"; // 중복된 아이디
    String DUPLICATE_EMAIL = "Duplicate Email"; // 중복된 이메일
    String DUPLICATE_PHONE = "Duplicate Phone"; // 중복된 전화번호
    
    String SIGN_IN_FAIL = "Login information mismatch"; // 로그인 정보 불일치
    String CERTIFICATION_FAIL = "Certification failed"; // 인증 실패
    String MAIL_FAIL = "Mail send failed"; // 메일 전송 실패
    String DATABASE_ERROR = "Database error"; // 데이터베이스 오류

    // 로그인 관련
    String ACCOUNT_LOCKED = "Account has been locked. Contact administrator."; // 계정 잠김, 관리자에게 문의 필요
    String CAPTCHA_FAIL = "Captcha validation failed. Please try again."; // 캡차 인증 실패
    String PASSWORD_FAIL_AFTER_CAPTCHA = "Captcha was successful, but the password is incorrect."; // 캡차 인증 성공 후 비밀번호 불일치
    
    // 아이디 및 비밀번호 찾기 관련
    String ID_FIND_SUCCESS = "ID retrieval was successful."; // 아이디 찾기 성공
    String ID_FIND_FAIL = "ID retrieval failed."; // 아이디 찾기 실패
    String FIND_SOCIAL_FAIL ="소셜로그인으로 가입하셨습니다. 소셜로그인으로 로그인해주세요";
    String PASSWORD_RESET_SUCCESS = "Password reset was successful."; // 비밀번호 초기화 성공
    String PASSWORD_RESET_FAIL = "Password reset failed."; // 비밀번호 초기화 실패

    String SOCIAL_AUTH_FAIL = "소셜 인증에 실패했습니다.";

}
