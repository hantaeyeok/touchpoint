package com.touchpoint.kh.user.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.touchpoint.kh.user.model.vo.Certificaion;

import jakarta.transaction.Transactional;

@Repository
public interface CertificaionRepository extends JpaRepository<Certificaion, String>{

	Certificaion findByUserId(String userId);
	
	@Transactional
	void deleteByUserId(String userId);


}
