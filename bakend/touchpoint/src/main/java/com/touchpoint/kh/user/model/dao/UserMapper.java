package com.touchpoint.kh.user.model.dao;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.touchpoint.kh.user.model.vo.User;

@Mapper
public interface UserMapper {
	User findByUserIdOrPhone(@Param("userIdOrPhone")String userIdOrPhone);
}
