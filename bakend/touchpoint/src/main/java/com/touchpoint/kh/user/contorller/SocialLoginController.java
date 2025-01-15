package com.touchpoint.kh.user.contorller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.touchpoint.kh.common.ResponseData;
import com.touchpoint.kh.common.ResponseHandler;
import com.touchpoint.kh.user.model.service.SocialLoginService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class SocialLoginController {

	private final ResponseHandler responseHandler;
    private final SocialLoginService socialLoginService;

	/*
	@GetMapping("/callback/{provider}/success")
	public ResponseEntity<ResponseData> success(@PathVariable("provider") String provider,
												Authentication authentication){
		
		OAuth2User user = (OAuth2User) authentication.getPrincipal();
		Map<String, Object> serviceData = socialLoginService.socialLogin(provider,user);
		
		return responseHandler.createResponse("데이터", serviceData, HttpStatus.OK);
	}
	*/
	@GetMapping("/callback/{provider}/success")
	public ResponseEntity<ResponseData> success(@PathVariable("provider") String provider, Authentication authentication) {
	    log.info("provider:{}",provider);
		log.info("authentioncaiton {}",authentication);
		if (authentication == null) {
	        return responseHandler.createResponse("Authentication object is null", authentication, HttpStatus.UNAUTHORIZED);
	
	    }

	    OAuth2User user = (OAuth2User) authentication.getPrincipal();
	    Map<String, Object> serviceData = socialLoginService.socialLogin(provider, user);

	    return responseHandler.createResponse("데이터", serviceData, HttpStatus.OK);
	}

}
