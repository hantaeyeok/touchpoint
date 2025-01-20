package com.touchpoint.kh.qna.model.service;

import java.util.List;

import com.touchpoint.kh.qna.model.vo.AnswerDto;
import com.touchpoint.kh.qna.model.vo.QnaDto;

public interface QnaService {

	public List<QnaDto> qnaFindAll();

	public int createQna(QnaDto qnaDto);

	public QnaDto qnaDetail(int qnaNo);

	public AnswerDto createAnswer(AnswerDto answer);

	public AnswerDto answerFind(int qnaNo);

}
