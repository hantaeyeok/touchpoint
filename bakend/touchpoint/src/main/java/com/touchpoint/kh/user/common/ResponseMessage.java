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
    
    // 사용자 상태 관련
    String USER_NOT_FOUND = "User not found. Please check your details and try again."; // 사용자 정보 없음
    String USER_ALREADY_ACTIVE = "Your account is already active."; // 계정 이미 활성화됨
    String USER_NOT_ACTIVE = "Your account is not active. Please activate your account."; // 계정 비활성화됨
    String UNAUTHORIZED = "Authentication failed. Please log in."; // 인증 실패
    String NO_COOKIE = "No authentication cookie found."; // 쿠키 없음
}



//String SUCCESS = "Request completed successfully."; // 성공
//String VALIDATION_FAIL = "Invalid input provided."; // 검증 실패
//String DUPLICATE_ID = "This ID is already in use."; // 중복 아이디
//String DUPLICATE_EMAIL = "This email is already in use."; // 중복 이메일
//String DUPLICATE_PHONE = "This phone number is already in use."; // 중복 전화번호
//
//String SIGN_IN_FAIL = "Invalid ID or password."; // 로그인 실패
//String CERTIFICATION_FAIL = "Certification failed."; // 인증 실패
//String MAIL_FAIL = "Failed to send email."; // 메일 전송 실패
//String DATABASE_ERROR = "An error occurred in the database."; // 데이터베이스 오류
//
//// 로그인 관련
//String ACCOUNT_LOCKED = "Account is locked. Contact support."; // 계정 잠김
//String CAPTCHA_FAIL = "Captcha verification failed."; // 캡차 실패
//String PASSWORD_FAIL_AFTER_CAPTCHA = "Captcha passed, but password is incorrect."; // 캡차 후 비밀번호 실패
//
//// 아이디 및 비밀번호 찾기 관련
//String ID_FIND_SUCCESS = "ID retrieval successful."; // 아이디 찾기 성공
//String ID_FIND_FAIL = "ID retrieval failed."; // 아이디 찾기 실패
//String PASSWORD_RESET_SUCCESS = "Password reset successful."; // 비밀번호 초기화 성공
//String PASSWORD_RESET_FAIL = "Password reset failed."; // 비밀번호 초기화 실패
//
//String SOCIAL_AUTH_FAIL = "Social authentication failed."; // 소셜 인증 실패
