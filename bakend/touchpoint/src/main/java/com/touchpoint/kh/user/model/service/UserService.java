package com.touchpoint.kh.user.model.service;

import com.touchpoint.kh.user.model.vo.User;

public interface UserService {
	
	//user id checked
	Boolean userIdChecked(String userId);
	
	//user email checked
	Boolean userEmailChecked(String email);
	
	// 
	User signupGeneralUser(User user);
	
	
}
