package com.touchpoint.kh.user.model.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.touchpoint.kh.user.model.vo.User;

@Mapper
public interface UserMapper {
	Optional<User> findByUserIdOrPhone(@Param("userIdOrPhone")String userIdOrPhone);
	
}
