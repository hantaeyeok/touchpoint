package com.touchpoint.kh.user.model.dao;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.touchpoint.kh.user.model.vo.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	boolean existsByUserId(String userId); 
	boolean existsByPhoneNo(String phone);
	boolean existsByEmail(String email);
	
	User findByUserId(String userId);
	User findByEmailAndPhoneNo(String email, String phone);
	User findByUserNameAndPhoneNo(String userName, String phone);
	User findByUserIdAndEmailAndPhoneNo(String userIdOrPhone,  String userId);
	
	
	
	
}
