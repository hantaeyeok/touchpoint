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
	User findByEmail(String email);
	
	User findByNameAndEmail(String name, String email);
	User findByNameAndPhoneNo(String name,String phoneNo);
		
	
	
	
	
}
