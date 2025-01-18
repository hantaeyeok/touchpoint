package com.touchpoint.kh.qna.model.vo;


import java.util.List;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AnswerDto {

	private int answerNo;
	private String answerTitle;
	private String answerContent;
	private String answerDate;
	private String answerStatus;
	
	private List<FileDto> files;
}
