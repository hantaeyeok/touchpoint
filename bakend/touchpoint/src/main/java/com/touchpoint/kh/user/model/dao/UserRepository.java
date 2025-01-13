package com.touchpoint.kh.user.model.dao;


import com.touchpoint.kh.user.model.vo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	//일반 회원 회원가입
	//사용자 id 중복 쳌
	Optional<User> findByUserId(String userId);
	
	//사용자 email 중복쳌
	Optional<User> findByEmail(String email);
	
}
