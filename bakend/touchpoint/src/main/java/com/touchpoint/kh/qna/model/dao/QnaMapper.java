package com.touchpoint.kh.qna.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.touchpoint.kh.qna.model.vo.AnswerDto;
import com.touchpoint.kh.qna.model.vo.FileDto;
import com.touchpoint.kh.qna.model.vo.QnaDto;

@Mapper
public interface QnaMapper {

	List<QnaDto> qnaFindAll();

	int insQna(QnaDto qnaDto);

	int insQnaFile(FileDto fileAdd);

	AnswerDto answerFind(int qnaNo);

	int insAnswer(AnswerDto answerDto);

	QnaDto qnaDetail(int qnaNo);

	void updateStatus(int qnaNo);

	void insAnswerFile(FileDto fileAdd);


}
