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
	public int createAnswer(AnswerDto answerDto) {
	    int rowsAffected = qnaMapper.insAnswer(answerDto); 
	    
	    if (rowsAffected > 0) {
	        qnaMapper.updateStatus(answerDto.getQnaNo());
	        return rowsAffected; 
	    } else {
	        throw new RuntimeException("답변 저장에 실패했습니다."); 
	    }
	}


	@Override
	public void createAnswerFile(FileDto fileAdd) {
		qnaMapper.insAnswerFile(fileAdd);
	}


	@Override
	public void updateQna(QnaDto qnaDto) {
		qnaMapper.updateQna(qnaDto);
	}


	@Override
	public void deleteFile(int fileNo) {
		qnaMapper.deleteFile(fileNo);
	}


//	@Transactional(rollbackOn = Exception.class) 안되는데 ㅡㅡㅗ
//	@Override
//	public int createQna(QnaDto qnaDto, List<FileDto> fileDtos) {
//	    // 1. 글 저장
//	    int rowsAffected = qnaMapper.insQna(qnaDto);
//	    if (rowsAffected <= 0) {
//	        throw new RuntimeException("글 저장에 실패했습니다.");
//	    }
//
//	    // 2. 파일 첨부 정보 저장
//	    if (fileDtos != null && !fileDtos.isEmpty()) {
//	        for (FileDto fileDto : fileDtos) {
//	            try {
//	                fileDto.setQnaNo(qnaDto.getQnaNo()); // 글 번호 설정
//	                int fileRows = qnaMapper.insQnaFile(fileDto);
//	                if (fileRows <= 0) {
//	                    throw new RuntimeException("파일 정보 저장에 실패했습니다.");
//	                }
//	            } catch (Exception e) {
//	                throw new RuntimeException("파일 첨부 실패: " + fileDto.getOriginName(), e);
//	            }
//	        }
//	    }
//
//	    return qnaDto.getQnaNo(); // 생성된 글 ID 반환
//	}
	
}
