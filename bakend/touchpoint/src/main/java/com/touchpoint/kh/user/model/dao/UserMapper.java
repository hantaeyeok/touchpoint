package com.touchpoint.kh.user.model.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.touchpoint.kh.user.model.vo.LoginAttempt;
import com.touchpoint.kh.user.model.vo.LoginAttemptMy;
import com.touchpoint.kh.user.model.vo.User;

@Mapper
public interface UserMapper {
	
	
	User findByUserIdOrPhone(@Param("userIdOrPhone")String userIdOrPhone);
	LoginAttemptMy attemptFindByUserId(@Param("userId")String userId);
	//User update
	int updateUser(User user);
	

    // LoginAttempt 삽입
    void insertLoginAttempt(LoginAttemptMy loginAttempt);

    // LoginAttempt 삭제
    void deleteLoginAttemptByUserId(@Param("userId") String userId);

    // LoginAttempt 업데이트
    int updateLoginAttempt(LoginAttemptMy loginAttempt);
}
