package com.touchpoint.kh.qna.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.touchpoint.kh.qna.model.vo.AnswerDto;
import com.touchpoint.kh.qna.model.vo.FileDto;
import com.touchpoint.kh.qna.model.vo.Qna;
import com.touchpoint.kh.qna.model.vo.QnaDto;

@Mapper
public interface QnaMapper {

	List<Qna> qnaFindAll();

	void insQna(QnaDto qnaDto);

	void insFile(FileDto fileAdd);

	void qnaDetail(int qnaNo);

	void answerFind(int qnaNo);

	void insAnswer(AnswerDto answer);

}
