package com.touchpoint.kh.qna.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.touchpoint.kh.qna.model.vo.AnswerDto;
import com.touchpoint.kh.qna.model.vo.FileDto;
import com.touchpoint.kh.qna.model.vo.QnaDto;

@Mapper
public interface QnaMapper {

	int insQna(QnaDto qnaDto);

	int insQnaFile(FileDto fileAdd);

	AnswerDto answerFind(int qnaNo);

	int insAnswer(AnswerDto answerDto);

	QnaDto qnaDetail(int qnaNo);

	void updateStatus(int qnaNo);

	void insAnswerFile(FileDto fileAdd);

	void updateQna(QnaDto qnaDto);

	void deleteFile(int fileNo);

	void updateAnswer(AnswerDto answerDto);

	void insNewFile(FileDto fileDto);

	int deleteQna(int qnaNo);

	int deleteAnswer(int qnaNo);
	
	List<QnaDto> qnaFindAllWithPaging(@Param("offset") int offset, @Param("size") int size);

	int qnaTotalCount();


}
