package com.touchpoint.kh.user.common.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.touchpoint.kh.user.model.dto.response.ResponseDto;

@RestControllerAdvice
public class ValidetionExceptionHandler {

	@ExceptionHandler({MethodArgumentNotValidException.class, HttpMessageNotReadableException.class})
	public ResponseEntity<ResponseDto> validetionExceptionHandler(Exception e) {
		return ResponseDto.validaionFail();
	
	}

}
