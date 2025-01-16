package com.touchpoint.kh.user.model.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.touchpoint.kh.user.model.vo.LoginAttempt;

import jakarta.transaction.Transactional;

@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long>{
	
	LoginAttempt findByUserId(String userId);
	
	@Transactional
	void deleteByUserId(String userId);

}
