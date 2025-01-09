package com.touchpoint.kh.user.contorller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.touchpoint.kh.common.ResponseData;
import com.touchpoint.kh.common.ResponseHandler;
import com.touchpoint.kh.common.message.LoginMessage;
import com.touchpoint.kh.user.model.service.UserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final ResponseHandler responseHandler;
	
	
	@GetMapping("/check-id")//ID CHECK
	public ResponseEntity<ResponseData> checkUserId(@RequestParam String userId) {
	    try {
	       return userService.userIdChecked(userId) //사용가능 true userid.isempty();
	    		   	? responseHandler.createResponse(LoginMessage.USER_ID_EXISTS_SUCCESS, null, HttpStatus.OK)
	    			: responseHandler.createResponse(LoginMessage.USER_ID_EXISTS, null, HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        return responseHandler.handleException("아이디 중복 확인 중 에러 발생", e);
	    }
	}
		
	@GetMapping("/check-email")// EMAIL CHECK
	public ResponseEntity<ResponseData> checkEmail(@RequestParam String email) {
	    try {
	       return userService.userIdChecked(email) //사용가능 true userid.isempty();
	    		   	? responseHandler.createResponse(LoginMessage.EMAIL_EXISTS_SUCCESS, null, HttpStatus.OK)
	    			: responseHandler.createResponse(LoginMessage.EMAIL_EXISTS, null, HttpStatus.BAD_REQUEST);
	    } catch (Exception e) {
	        return responseHandler.handleException("이메일 중복 확인 중 에러 발생", e);
	    }
	}
	
	@PostMapping("/signup")
	public ResponseEntity<ResponseData> singup(){
		
		return ResponseEntity.ok().body(null);
	}
	
}
