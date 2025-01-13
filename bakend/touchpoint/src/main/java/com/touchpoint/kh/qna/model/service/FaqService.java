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

	public void deleteFaq(int faqNo) {
		/*Faq deleteFaq = faqRepository.findById(faqNo)
									 .orElseThrow(()-> new RuntimeException("FAQ not found with faqNo :"+faqNo));
		faqNo 가 pk이기 때문에 없으면 예외처리가 자동으로됨
		*/
		faqRepository.deleteById(faqNo);
	}
	
}
