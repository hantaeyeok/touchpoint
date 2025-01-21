package com.touchpoint.kh.user.common;

public interface ResponseCode {

	String SUCCESS ="SU";
	String VALIDATION_FAIL = "VF";
	String DUPLICATE_ID = "DI";
	String DUPLICATE_EMAIL = "DE";
	String DUPLICATE_PHONE = "DP";
	String SIGN_IN_FAIL ="SF";
	
	String CERTIFICATION_FALI = "CF";
	
	String MAIL_FAIL = "MF";
	String DATABASE_ERROR = "DBE";
	
	// Login specific
    String ACCOUNT_LOCKED = "AL";
    String CAPTCHA_FAIL = "CF";
    
    String PASSWORD_FAIL_AFTER_CAPTCHA = "PFAC";
}
