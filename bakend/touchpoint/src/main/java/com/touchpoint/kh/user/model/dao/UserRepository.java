package com.touchpoint.kh.user.model.dao;


import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.touchpoint.kh.user.model.vo.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	
	//사용자 id 중복 쳌
	Optional<User> findByUserId(String userId);
	
	//사용자 email 중복쳌
	Optional<User> findByEmail(String email);
	
	//
	Optional<User> findByPhoneNo(String phoneNo);
	
	@Query("SELECT USERS FROM USERS WHERE userId = :userIdOrPhone OR phone = :userIdOrPhone")
	Optional<User> findByUserIdOrPhone(@Param("userIdOrPhone") String userIdOrPhone);
	
}
