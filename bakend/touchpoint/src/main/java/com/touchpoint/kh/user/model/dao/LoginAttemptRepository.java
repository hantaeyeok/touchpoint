package com.touchpoint.kh.user.model.dao;


import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.touchpoint.kh.user.model.vo.LoginAttempt;


@Repository
public interface LoginAttemptRepository extends JpaRepository<LoginAttempt, Long>{
	
	@Query("SELECT l FROM LoginAttempt l WHERE l.userId = :userId")
	LoginAttempt findByUserId(@Param("userId") String userId);
	
	void deleteByUserId(String userId);

}
