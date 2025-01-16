package com.touchpoint.kh.user.contorller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.touchpoint.kh.common.ResponseData;
import com.touchpoint.kh.common.ResponseHandler;
import com.touchpoint.kh.common.message.LoginMessage;
import com.touchpoint.kh.user.model.dto.request.CheckCertificaionRequestDto;
import com.touchpoint.kh.user.model.dto.request.EmailCertificaionRequsetDto;
import com.touchpoint.kh.user.model.dto.request.IdCheckRequestDto;
import com.touchpoint.kh.user.model.dto.request.SignInRequestDto;
import com.touchpoint.kh.user.model.dto.response.CheckCertificaionResponseDto;
import com.touchpoint.kh.user.model.dto.response.EmailCertificaionResponseDto;
import com.touchpoint.kh.user.model.dto.response.IdCheckResponseDto;
import com.touchpoint.kh.user.model.dto.response.SignInResponseDto;
import com.touchpoint.kh.user.model.dto.response.SignUpRequestDto;
import com.touchpoint.kh.user.model.dto.response.SignUpResponseDto;
import com.touchpoint.kh.user.model.service.UserService;
import com.touchpoint.kh.user.model.vo.User;
import com.touchpoint.kh.user.model.vo.UserDto;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final ResponseHandler responseHandler;
	
	@GetMapping("/check-id")//ID CHECK
	public ResponseEntity<ResponseData> checkUserId(@RequestParam("userId") String userId) {
	    try {
	       return userService.userIdChecked(userId) //사용가능 true userid.isempty();
	    		   	? responseHandler.createResponse(LoginMessage.USER_ID_EXISTS_SUCCESS, true, HttpStatus.OK)
	    			: responseHandler.createResponse(LoginMessage.USER_ID_EXISTS, false, HttpStatus.OK);
	    } catch (Exception e) {
	        return responseHandler.handleException("아이디 중복 확인 중 에러 발생", e);
	    }
	}
		
	@GetMapping("/check-email")// EMAIL CHECK
	public ResponseEntity<ResponseData> checkEmail(@RequestParam("email") String email) {
	    try {
	       return userService.userEmailChecked(email) //사용가능 true userid.isempty();
	    		   	? responseHandler.createResponse(LoginMessage.EMAIL_EXISTS_SUCCESS, true, HttpStatus.OK)
	    			: responseHandler.createResponse(LoginMessage.EMAIL_EXISTS, false, HttpStatus.OK);
	    } catch (Exception e) {
	        return responseHandler.handleException("이메일 중복 확인 중 에러 발생", e);
	    }
	}
	
	@GetMapping("/check-phone")
	public ResponseEntity<ResponseData> checkPhone(@RequestParam("phone") String phone) {
	    try {
	       return userService.userPhoneChecked(phone) //사용가능 true userid.isempty();
	    		   	? responseHandler.createResponse("사용가능", true, HttpStatus.OK)
	    			: responseHandler.createResponse("불가", false, HttpStatus.OK);
	    } catch (Exception e) {
	        return responseHandler.handleException("전화번호 중복체크", e);
	    }
	}
	
	
	//일반회원가입
	@PostMapping("/signup")
	public ResponseEntity<ResponseData> signupGeneralUser(@RequestBody UserDto userDto){
	    try {
	    	
	    	User savedUser =userService.signupGeneralUser(userDto);
	    	
	    	return savedUser.getUserId().equals(userDto.getUserId())
	    			? responseHandler.createResponse(LoginMessage.SIGNUP_SUCCESS, true, HttpStatus.OK)
	    			: responseHandler.createResponse(LoginMessage.SIGNUP_FAILURE, false, HttpStatus.INTERNAL_SERVER_ERROR);
	    } catch (Exception e) {
	        return responseHandler.handleException("회원가입 중 에러 발생", e);
	    }
	}
	
	
	//소셜 회원가입 로직 
	
	
	@GetMapping("/socal-user")
	public ResponseEntity<ResponseData> getSocialUserInfo(@AuthenticationPrincipal OAuth2User oAuth2User) {
		 try {
		        if (oAuth2User == null) {
		            return responseHandler.createResponse("소셜 로그인 정보가 없습니다.", false, HttpStatus.UNAUTHORIZED);
		        }

		        Map<String, Object> userAttributes = oAuth2User.getAttributes();
		        return responseHandler.createResponse("소셜 사용자 정보 반환 성공", userAttributes, HttpStatus.OK);
		    } catch (Exception e) {
		        return responseHandler.handleException("소셜 사용자 정보를 가져오는 중 에러 발생", e);
		    }
	}
	
	
	@PostMapping("/id-dheck")
	public ResponseEntity<? super IdCheckResponseDto> idCheck(
			@RequestBody @Valid IdCheckRequestDto requestBody){
		ResponseEntity<? super IdCheckResponseDto> response = userService.idcheck(requestBody);
		return response;
	}
	
	
	@PostMapping("email-certificaion")
	public ResponseEntity<? super EmailCertificaionResponseDto> emailCertificaion(
			@RequestBody @Valid EmailCertificaionRequsetDto requsetBody){
		ResponseEntity<? super EmailCertificaionResponseDto> response = userService.emailCertification(requsetBody);
		return response;
		
	}
	
	
	@PostMapping("/check-certificaion")
	public ResponseEntity<? super CheckCertificaionResponseDto> checkCertificaio(
			@RequestBody @Valid CheckCertificaionRequestDto requsetBody){
		ResponseEntity<? super CheckCertificaionResponseDto> response = userService.checkCertificaion(requsetBody);
		return response;
	}
	
	@PostMapping("sign-up")
	public ResponseEntity<? super SignUpResponseDto> signUp(
			@RequestBody @Valid SignUpRequestDto requestBody){
		
		ResponseEntity<? super SignUpResponseDto> response = userService.signUp(requestBody);
		return response;
		
	}
	
	
	@PostMapping("/sign-in")
	public ResponseEntity<? super SignInResponseDto> signIn(
			@RequestBody @Valid SignInRequestDto responsebody){
		ResponseEntity<? super SignInResponseDto> response = userService.signIn(responsebody);
		return response;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
