package com.touchpoint.kh.user.common;

public interface ResponseMessage {
	String SUCCESS ="Success";
	String VALIDATION_FAIL = "Validation failed";
	String DUPLICATE_ID = "Duplicate Id";
	String DUPLICATE_EMAIL = "Duplicate Eamil";
	String DUPLICATE_PHONE = "Duplicate Phone";
	
	
	String SIGN_IN_FAIL ="Login information mismatch";
	String CERTIFICATION_FALI = "Cretification failed";
	String MAIL_FAIL = "Mail send failed";
	String DATABASE_ERROR = "Database error";
}
