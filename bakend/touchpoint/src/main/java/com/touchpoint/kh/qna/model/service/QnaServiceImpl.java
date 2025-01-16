package com.touchpoint.kh.qna.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.touchpoint.kh.qna.model.dao.QnaMapper;
import com.touchpoint.kh.qna.model.vo.FileDto;
import com.touchpoint.kh.qna.model.vo.Qna;
import com.touchpoint.kh.qna.model.vo.QnaDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class QnaServiceImpl implements QnaService, FileService {

	private final QnaMapper qnaMapper;

	@Override
	public List<Qna> qnaFindAll() {
		return qnaMapper.qnaFindAll();
	}

	@Override
	public void createQna(QnaDto qnaDto) {
		qnaMapper.insQna(qnaDto);
	}

	@Override
	public void createFile(FileDto fileAdd) {
		qnaMapper.insFile(fileAdd);
	}
}
