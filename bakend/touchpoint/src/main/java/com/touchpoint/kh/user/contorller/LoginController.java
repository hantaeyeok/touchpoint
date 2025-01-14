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

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

	private final UserService userService;
	private final ResponseHandler responseHandler;
	 
	 @PostMapping("/login")
	    public ResponseEntity<ResponseData> login(@RequestBody LoginRequest loginRequest) {
	        try {
	            ResponseData response = userService.validateLogin(loginRequest);
	            return responseHandler.createResponse(response.getMessage(), response.getData(), HttpStatus.OK);
	        } catch (Exception e) {
	            return responseHandler.handleException("로그인실패", e);
	        }
	    }
	 
	 @PostMapping("/logout")
	 public ResponseEntity<String> logout(HttpServletRequest request) {
		    return ResponseEntity.ok("로그아웃 성공");
	 }
	 
	 @PostMapping("/logout")
	    public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
	        try {
	            // 인증 정보 가져오기
	            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

	            if (authentication != null && authentication.getName() != null) {
	                // 사용자 ID를 기반으로 활성 세션 비활성화
	                userSessionRepository.findByUserIdAndSessionStatus(authentication.getName(), "A")
	                    .ifPresent(session -> {
	                        session.setSessionStatus("I"); // 세션 비활성화
	                        userSessionRepository.save(session);
	                    });
	            }

	            // SecurityContext 및 세션 무효화
	            request.getSession().invalidate();
	            SecurityContextHolder.clearContext();

	            return ResponseEntity.ok("로그아웃 성공");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("로그아웃 실패");
	        }
	    }
	 
	 
	 

    /*
     *  @PostMapping
	 public ResponseEntity<ResponseData> login(@RequestBody LoginRequest loginRequest) {
		 try {
			ResponseData responseData = userService.validateLogin(loginRequest);
			switch (responseData.getMessage()) {
				case "로그인 성공":
                    return responseHandler.createResponse(LoginMessage.LOGIN_SUCCESS, responseData.getData(), HttpStatus.OK);
                case "캡차 검증에 실패했습니다. 다시 시도하세요.":
                    return responseHandler.createResponse(responseData.getMessage(), null, HttpStatus.UNAUTHORIZED);
                case "계정이 잠겼습니다. 관리자에게 문의하세요.":
                    return responseHandler.createResponse(responseData.getMessage(), null, HttpStatus.FORBIDDEN);
                default:
                    return responseHandler.createResponse(responseData.getMessage(), null, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
            return responseHandler.handleException("로그인 시도 중 에러 발생", e);
		}
	 }
     * */
}
