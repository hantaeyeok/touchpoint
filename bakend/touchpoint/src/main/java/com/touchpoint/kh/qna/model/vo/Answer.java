package com.touchpoint.kh.qna.model.vo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@SequenceGenerator(
		name = "answer_seq_generator",
		sequenceName = "answer_SEQ",
		initialValue = 1,
		allocationSize = 1
		)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "ANSWER")
public class Answer {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="answer_seq_generator" )
	@Column(name = "ANSWER_NO")
	private int answerNo;
	
	@Column(name = "ANSWER_TITLE", nullable = false)
	private String answerTitle;

	@Column(name = "ANSWER_CONTENT", nullable = false)
	private String answerContent;
	
	@Column(name = "ANSWER_DATE", columnDefinition = "DATE DEFAULT SYSDATE", nullable = false)
	private LocalDateTime answerDate;
	
	@Column(name = "ANSWER_STATUS", columnDefinition = "VARCHAR2(20) DEFAULT '질문중'" , nullable = false)
	private String answerStatus;
}
