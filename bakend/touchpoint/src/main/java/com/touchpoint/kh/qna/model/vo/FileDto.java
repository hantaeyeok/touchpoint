package com.touchpoint.kh.qna.model.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileDto {
	
	private int fileNo;
	private String originName;
	private String changeName;
	private int qnaNo;
	private int answerNo;
	private String path;
	
	private String absolutePath;  // 절대 경로 추가
	
}
