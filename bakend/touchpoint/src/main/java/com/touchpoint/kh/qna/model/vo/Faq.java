package com.touchpoint.kh.qna.model.vo;

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
		name = "faq_seq_generator",
		sequenceName = "faq_SEQ",
		initialValue = 1,
		allocationSize = 1
		)
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name= "FAQ")
public class Faq {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator ="faq_seq_generator" )
	@Column(name = "FAQ_NO")
	private int faqNo;

	@Column(name = "FAQ_TITLE", nullable = false)
	private String faqTitle;
	
	@Column(name = "ANSWER", nullable = false)
	private String answer;
}
