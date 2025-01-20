package com.touchpoint.kh.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ResponseHandler {
	
	// 공통 성공 응답
	public ResponseEntity<ResponseData> createResponse(String message, Object data, HttpStatus status){
		return ResponseEntity.status(status)
				.body(ResponseData.builder().data(data).message(message).build());
	}
	
	// 공통 응답 실패
	public ResponseEntity<ResponseData> handleException(String errorMessage, Exception e) {
		log.error("{}: {}", errorMessage, e);
        return createResponse(errorMessage + ": " + e.getMessage(), "error", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
