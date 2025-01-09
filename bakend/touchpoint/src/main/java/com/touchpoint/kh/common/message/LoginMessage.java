package com.touchpoint.kh.common.message;

public class LoginMessage {
	//로그인 관련 메시지
	public static final String LOGIN_SUCCESS = "로그인 성공";
	public static final String LOGIN_FAILURE = "아이디 또는 비밀번호가 잘못되었습니다.";
	public static final String USER_NOT_FOUND = "사용자를 찾을 수 없습니다.";
	public static final String PASSWORD_MISMATCH = "비밀번호가 일치하지 않습니다.";
	
	
	//회원가입 관련 메시지
    public static final String SIGNUP_SUCCESS = "회원가입 성공";
    public static final String USER_ID_EXISTS = "이미 사용 중인 사용자 ID입니다.";
    public static final String USER_ID_EXISTS_SUCCESS = "사용 가능한 ID입니다.";
    
    public static final String EMAIL_EXISTS = "이미 사용 중인 이메일입니다.";
    public static final String EMAIL_EXISTS_SUCCESS = "사용 가능한 이메일입니다.";
    
    public static final String SOCIAL_SIGNUP_SUCCESS = "소셜 회원가입 성공";
    public static final String INVALID_SOCIAL_INFO = "소셜 로그인 사용자는 필수 정보를 입력해야 합니다.";
}
