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
public class QnaDto {

	private int qnaNo;
	private String qnaTitle;
	private String qnaContent;
	private String qnaDate;
	private String userId;
	private int phoneNo;
	private String status;
	
	private List<FileDto> files;
}
