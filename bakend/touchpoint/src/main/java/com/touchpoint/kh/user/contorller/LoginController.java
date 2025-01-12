package com.touchpoint.kh.user.contorller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.touchpoint.kh.common.ResponseData;
import com.touchpoint.kh.common.ResponseHandler;
import com.touchpoint.kh.common.message.LoginMessage;
import com.touchpoint.kh.user.model.service.UserService;
import com.touchpoint.kh.user.model.vo.LoginRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class LoginController {

	private final UserService userService;
	private final ResponseHandler responseHandler;
    
	/*
    @PostMapping
    public ResponseEntity<ResponseData> login(@RequestBody LoginRequest loginRequest) {
    	try {
    		return userService.validateLogin(loginRequest) //true : 일치
        			? responseHandler.createResponse(LoginMessage.LOGIN_SUCCESS, true, HttpStatus.OK)
        			: responseHandler.createResponse(LoginMessage.LOGIN_FAILURE, false, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
	        return responseHandler.handleException("로그인 시도 중 에러 발생", e);
		}    	
    }
    */
    

}
