package com.touchpoint.kh.qna.model.vo;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {

	private int answerNo;
	private String answerTitle;
	private String answerContent;
	private String answerDate;
	private int qnaNo;
	
	private List<FileDto> files;
}
