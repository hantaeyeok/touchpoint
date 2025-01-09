package com.touchpoint.kh.user.model.dao;


import com.touchpoint.kh.user.model.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	//일반 회원 회원가입
	//사용자 id로 사용자 조회
	Optional<User> findByUserId(String userId);
	Optional<User> findByEmail(String email);
	
}
