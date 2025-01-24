package com.touchpoint.kh.qna.model.service;

import java.util.List;

import com.touchpoint.kh.qna.model.vo.AnswerDto;
import com.touchpoint.kh.qna.model.vo.FileDto;
import com.touchpoint.kh.qna.model.vo.QnaDto;

public interface QnaService {

	public List<QnaDto> qnaFindAll();

	public int createQna(QnaDto qnaDto);

	public QnaDto qnaDetail(int qnaNo);

	public int createAnswer(AnswerDto answerDto);

	public AnswerDto answerFind(int qnaNo);

	public void createQnaFile(FileDto fileAdd);

	public void createAnswerFile(FileDto fileAdd);

	public void updateQna(QnaDto qnaDto);

	public void deleteFile(int fileNo);

	public void updateAnswer(AnswerDto answerNo);

	public void insNewFile(FileDto fileDto);

	//public int createQna(QnaDto qnaDto, List<FileDto> fileDtos);


}
