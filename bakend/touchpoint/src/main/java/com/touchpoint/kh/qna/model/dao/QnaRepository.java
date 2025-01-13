package com.touchpoint.kh.qna.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.touchpoint.kh.qna.model.vo.Qna;

@Repository
public interface QnaRepository extends JpaRepository<Qna, Integer> {

	//@Query("SELECT u FROM User u WHERE u.name = ?1") 명시적 쿼리 작성 가능
}
