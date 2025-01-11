package com.touchpoint.kh.user.model.service;

import com.touchpoint.kh.common.ResponseData;
import com.touchpoint.kh.user.model.vo.LoginRequest;
import com.touchpoint.kh.user.model.vo.User;
import com.touchpoint.kh.user.model.vo.UserDto;

public interface UserService {
	
	//user id checked
	Boolean userIdChecked(String userId);
	
	//user email checked
	Boolean userEmailChecked(String email);
	
	//user signupGeneralUser
	User signupGeneralUser(UserDto userDto);
	
	//GeneralUser-login
	ResponseData validateLogin(LoginRequest loginRequest);
	
	
}
