package com.touchpoint.kh.qna.model.service;

import java.util.List;

import com.touchpoint.kh.qna.model.vo.Qna;
import com.touchpoint.kh.qna.model.vo.QnaDto;

public interface QnaService {

	public List<Qna> qnaFindAll();

	public void createQna(QnaDto qnaDto);

}
