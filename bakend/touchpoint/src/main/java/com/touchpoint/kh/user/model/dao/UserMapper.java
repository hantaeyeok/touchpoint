package com.touchpoint.kh.user.model.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.touchpoint.kh.user.model.vo.LoginAttempt;
import com.touchpoint.kh.user.model.vo.LoginAttemptMy;
import com.touchpoint.kh.user.model.vo.User;

@Mapper
public interface UserMapper {
	
	//UserIdOrPhoneNo 아이디 또는 전화번호로 User 검색.
	User findByUserIdOrPhone(@Param("userIdOrPhone")String userIdOrPhone);
    User findByUserIdAndEmailAndPhoneNo(@Param("userIdOrPhone") String userIdOrPhone, @Param("userId") String userId);
    User findUserByProviderUserId(String providerUserId);

}
