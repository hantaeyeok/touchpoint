package com.touchpoint.kh;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.touchpoint.kh.user.model.dao.LoginAttemptRepository;
import com.touchpoint.kh.user.model.dao.UserMapper;
import com.touchpoint.kh.user.model.dao.UserRepository;

@SpringBootTest

class TouchpointApplicationTests{

	@Autowired
	private  UserRepository userRepository;
	@Autowired
	private  LoginAttemptRepository loginAttemptRepository;
	@Autowired
	private  UserMapper userMapper;
	
	
	@Test
	void testUpdateLoginAttempt() {
		
	}




}
