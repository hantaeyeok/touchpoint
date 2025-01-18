package com.touchpoint.kh.qna.model.service;

import java.util.List;

import com.touchpoint.kh.qna.model.vo.AnswerDto;
import com.touchpoint.kh.qna.model.vo.Qna;
import com.touchpoint.kh.qna.model.vo.QnaDto;

public interface QnaService {

	public List<Qna> qnaFindAll();

	public QnaDto createQna(QnaDto qnaDto);

	public QnaDto qnaDetail(int qnaNo);

	public AnswerDto answerFind(int qnaNo);

	public AnswerDto createAnswer(AnswerDto answer);

}
