package com.touchpoint.kh.qna.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.touchpoint.kh.qna.model.dao.FaqRepository;
import com.touchpoint.kh.qna.model.vo.Faq;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FaqService {
	
	private final FaqRepository faqRepository;
	
	public List<Faq> getAllFaqs() {
		return faqRepository.findAll();
	}

	public Faq createFaq(Faq faq) {
		return faqRepository.save(faq);
	}

	public Faq updateFaq(Faq faqDetails, int faqNo) {
		Faq originFaq = faqRepository.findById(faqNo)
								  .orElseThrow(()-> new RuntimeException("FAQ not found with faqNo :"+faqNo));
		originFaq.setFaqTitle(faqDetails.getFaqTitle());
		originFaq.setAnswer(faqDetails.getAnswer());
		
		return faqRepository.save(originFaq);
	}
	
	
}
