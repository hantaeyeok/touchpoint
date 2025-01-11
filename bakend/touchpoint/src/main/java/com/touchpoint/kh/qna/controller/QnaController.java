package com.touchpoint.kh.qna.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.touchpoint.kh.qna.model.service.FaqService;
import com.touchpoint.kh.qna.model.vo.Faq;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnaController {
	
	private final FaqService faqService;
	
	@GetMapping
	public List<Faq> getAllFaqs(){
		return faqService.getAllFaqs();
	}
	
	@PostMapping("/createFaq")
	public ResponseEntity<Faq> createFaq(@RequestBody Faq faq) {
		Faq createFaq = faqService.createFaq(faq);
		return ResponseEntity.ok(createFaq);
	}
	
	@PutMapping("/update/{faqNo}")
	public ResponseEntity<Faq> updateFaq(@RequestBody Faq faqDetails,
										 @PathVariable int faqNo){
		Faq updateFaq = faqService.updateFaq(faqDetails, faqNo);
		return ResponseEntity.ok(updateFaq);
	}
	
}
