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
public class QnaServiceImpl implements QnaService {

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
	public void createQnaFile(FileDto fileAdd) {
		qnaMapper.insQnaFile(fileAdd);
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
	public int createAnswer(AnswerDto answer) {
	    int rowsAffected = qnaMapper.insAnswer(answer); 
	    if (rowsAffected > 0) {
	        qnaMapper.updateStatus(answer.getQnaNo()); 
	        return rowsAffected; 
	    } else {
	        throw new RuntimeException("답변 저장에 실패했습니다."); 
	    }
	}


	@Override
	public void createAnswerFile(FileDto fileAdd) {
		qnaMapper.insAnswerFile(fileAdd);
	}
	
}
