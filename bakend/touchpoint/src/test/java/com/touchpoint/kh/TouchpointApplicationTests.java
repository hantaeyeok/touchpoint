package com.touchpoint.kh;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.touchpoint.kh.user.model.dao.LoginAttemptRepository;
import com.touchpoint.kh.user.model.dao.UserMapper;
import com.touchpoint.kh.user.model.dao.UserRepository;
import com.touchpoint.kh.user.model.vo.LoginAttemptMy;

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
	    String userId = "test2";
	    LoginAttemptMy attempt = LoginAttemptMy.builder()
	            .userId(userId)
	            .failedLoginCnt(8)
	            .captchaActive("Y")
	            .build();

	    // 업데이트 실행
	    int rowsAffected = userMapper.updateLoginAttempt(attempt);

	    // 업데이트 결과 출력
	    System.out.println("Rows affected: " + rowsAffected);

	    // 업데이트된 데이터 확인
	    LoginAttemptMy updatedAttempt = userMapper.attemptFindByUserId(userId);
	    System.out.println("Updated record: " + updatedAttempt);

	    // Assertions
	    assertNotNull(updatedAttempt, "Updated attempt should not be null");
	    assertEquals(8, updatedAttempt.getFailedLoginCnt(), "Failed login count should be updated to 7");
	    assertEquals("Y", updatedAttempt.getCaptchaActive(), "Captcha active should be updated to 'Y'");
	}




}
