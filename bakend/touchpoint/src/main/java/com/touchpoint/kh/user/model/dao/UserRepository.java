package com.touchpoint.kh.user.model.dao;


import java.util.Optional;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.touchpoint.kh.user.model.vo.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	boolean existByUserId(String userId);
	
	
	//사용자 id 중복 쳌
	User findByUserId(String userId);
	
	//사용자 email 중복쳌
	Optional<User> findByEmail(String email);
	
	//
	Optional<User> findByPhoneNo(String phoneNo);
	
	//아래 코드 동작 안해서 mapper에함
	@Query("SELECT u FROM User u WHERE u.userId = :userIdOrPhone OR u.phoneNo = :userIdOrPhone")
    Optional<User> findByUserIdOrPhone(@Param("userIdOrPhone") String userIdOrPhone);
	
}
