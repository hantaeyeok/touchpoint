package com.touchpoint.kh.qna.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.touchpoint.kh.qna.model.dao.QnaMapper;
import com.touchpoint.kh.qna.model.vo.AnswerDto;
import com.touchpoint.kh.qna.model.vo.FileDto;
import com.touchpoint.kh.qna.model.vo.QnaDto;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService, FileService {

	private final QnaMapper qnaMapper;

	@Override
	public List<QnaDto> qnaFindAll() {
		return qnaMapper.qnaFindAll();
	}

	@Override
	public int  createQna(QnaDto qnaDto) {
		return qnaMapper.insQna(qnaDto);
	}

	@Override
	public void createFile(FileDto fileAdd) {
		qnaMapper.insFile(fileAdd);
	}

	@Override
	public QnaDto qnaDetail(int qnaNo) {
		return qnaMapper.qnaDetail(qnaNo);
	}

	@Override
	public AnswerDto answerFind(int qnaNo) {
		return qnaMapper.answerFind(qnaNo);
	}

	@Override
	@Transactional
	public AnswerDto createAnswer(AnswerDto answer) {
		qnaMapper.insAnswer(answer);
	    qnaMapper.updateStatus(answer.getQnaNo());
	    return answer; 
	}

}
