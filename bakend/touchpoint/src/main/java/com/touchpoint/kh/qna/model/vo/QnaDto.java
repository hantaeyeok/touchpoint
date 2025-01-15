package com.touchpoint.kh.qna.model.vo;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QnaDto {

	private int qnaNo;
	private String qnaTitle;
	private String qnaContent;
	private LocalDateTime qnaDate;
	private String userId;
	private int phoneNo;
}
