package com.touchpoint.kh.qna.model.service;

import java.util.List;

import com.touchpoint.kh.qna.model.vo.AnswerDto;
import com.touchpoint.kh.qna.model.vo.Qna;
import com.touchpoint.kh.qna.model.vo.QnaDto;

public interface QnaService {

	public List<Qna> qnaFindAll();

	public void createQna(QnaDto qnaDto);

	public void qnaDetail(int qnaNo);

	public void answerFind(int qnaNo);

	public void createAnswer(AnswerDto answer);

}
