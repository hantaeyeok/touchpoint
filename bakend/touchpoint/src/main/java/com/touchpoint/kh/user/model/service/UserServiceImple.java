package com.touchpoint.kh.user.model.service;

import org.springframework.stereotype.Service;
import com.touchpoint.kh.user.model.dao.UserRepository;
import com.touchpoint.kh.user.model.vo.User;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImple implements UserService{

	
	private final UserRepository userRepository;

	//user id checked
	@Override
	public Boolean userIdChecked(String userId) {
		return userRepository.findByUserId(userId).isEmpty();
	}
	
	@Override
	public Boolean userEmailChecked(String email) {
		return userRepository.findByEmail(email).isEmpty();
	}
	
	
	@Override
	public User signupGeneralUser(User user) {
		return user;
	}




	



}
