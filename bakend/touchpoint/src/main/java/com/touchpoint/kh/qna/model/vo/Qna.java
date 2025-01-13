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
		name = "qna_seq_generator",
		sequenceName = "QNA_SEQ",
		initialValue = 1,
		allocationSize = 1
		)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "QNA")
public class Qna {
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="qna_seq_generator" )
	@Column(name = "QNA_NO")
	private int qnaNo;
	
	@Column(name = "QNA_TITLE", nullable = false)
	private String qnaTitle;
	
	@Column(name = "QNA_CONTENT", nullable = false)
	private String qnaContent;
	
	@Column(name = "QNA_DATE", columnDefinition = "DATE DEFAULT SYSDATE", nullable = false )
	private LocalDateTime qnaDate; //JPA 에서 DATE SYSDATE 하려면 string 대신 이게 적합
	
	@Column(name = "USER_ID", nullable = false)
	private String userId;

	@Column(name = "PHONE_NO")
	private int phoneNo;
}
