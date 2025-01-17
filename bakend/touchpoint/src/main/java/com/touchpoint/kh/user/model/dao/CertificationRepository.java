package com.touchpoint.kh.user.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.touchpoint.kh.user.model.vo.Certification;

import jakarta.transaction.Transactional;

@Repository
public interface CertificationRepository extends JpaRepository<Certification, String>{

	Certification findByUserId(String userId);
	
	@Transactional
	void deleteByUserId(String userId);


}
