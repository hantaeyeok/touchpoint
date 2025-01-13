package com.touchpoint.kh.qna.model.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.touchpoint.kh.qna.model.vo.Faq;
@Repository
public interface FaqRepository extends JpaRepository<Faq, Integer> {
	
	
}
