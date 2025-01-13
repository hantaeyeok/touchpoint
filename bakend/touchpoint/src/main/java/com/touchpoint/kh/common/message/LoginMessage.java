package com.touchpoint.kh.common.message;

public class LoginMessage {
    // 로그인 관련 메시지
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGIN_FAILURE = "아이디 또는 비밀번호가 잘못되었습니다.";
    public static final String USER_NOT_FOUND = "사용자를 찾을 수 없습니다.";
    public static final String PASSWORD_MISMATCH = "비밀번호가 일치하지 않습니다.";
    public static final String ACCOUNT_LOCKED = "계정이 잠겨있습니다. 관리자에게 문의하세요.";
    public static final String ACCOUNT_DISABLED = "비활성화된 계정입니다. 관리자에게 문의하세요.";
    public static final String LOGIN_ATTEMPT_LIMIT_EXCEEDED = "로그인 시도 제한을 초과했습니다.";

    // 회원가입 관련 메시지
    public static final String SIGNUP_SUCCESS = "회원가입 성공";
    public static final String SIGNUP_FAILURE = "회원가입 실패: 입력 정보를 확인해주세요.";
    public static final String USER_ID_EXISTS = "이미 사용 중인 사용자 ID입니다.";
    public static final String USER_ID_EXISTS_SUCCESS = "사용 가능한 ID입니다.";
    public static final String EMAIL_EXISTS = "이미 사용 중인 이메일입니다.";
    public static final String EMAIL_EXISTS_SUCCESS = "사용 가능한 이메일입니다.";
    public static final String INVALID_INPUT = "유효하지 않은 입력 값입니다.";
    public static final String PASSWORD_POLICY_VIOLATION = "비밀번호는 최소 8자 이상, 영문, 숫자, 특수문자를 포함해야 합니다.";

    // 소셜 로그인 관련 메시지
    public static final String SOCIAL_SIGNUP_SUCCESS = "소셜 회원가입 성공";
    public static final String SOCIAL_SIGNUP_FAILURE = "소셜 회원가입 실패: 입력 정보를 확인해주세요.";
    public static final String INVALID_SOCIAL_INFO = "소셜 로그인 사용자는 필수 정보를 입력해야 합니다.";
    public static final String SOCIAL_LOGIN_SUCCESS = "소셜 로그인 성공";
    public static final String SOCIAL_LOGIN_FAILURE = "소셜 로그인 실패: 제공된 정보가 유효하지 않습니다.";
    public static final String SOCIAL_ACCOUNT_LINKED = "소셜 계정이 이미 연동되었습니다.";
    public static final String SOCIAL_ACCOUNT_NOT_FOUND = "소셜 계정을 찾을 수 없습니다.";

    // 공통 실패 메시지
    public static final String GENERAL_FAILURE = "요청 처리 중 오류가 발생했습니다.";
    public static final String SERVER_ERROR = "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.";
    public static final String ACCESS_DENIED = "접근 권한이 없습니다.";
    public static final String INVALID_TOKEN = "유효하지 않은 토큰입니다.";
    public static final String TOKEN_EXPIRED = "토큰이 만료되었습니다. 다시 로그인해주세요.";
}
