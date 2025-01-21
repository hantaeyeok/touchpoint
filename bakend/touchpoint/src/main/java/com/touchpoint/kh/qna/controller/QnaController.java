package com.touchpoint.kh.qna.controller;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.touchpoint.kh.common.ResponseData;
import com.touchpoint.kh.common.ResponseHandler;
import com.touchpoint.kh.qna.model.service.FaqService;
import com.touchpoint.kh.qna.model.service.QnaService;
import com.touchpoint.kh.qna.model.vo.AnswerDto;
import com.touchpoint.kh.qna.model.vo.Faq;
import com.touchpoint.kh.qna.model.vo.FileDto;
import com.touchpoint.kh.qna.model.vo.QnaDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/qna")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class QnaController {
	
	private final FaqService faqService;
	private final QnaService qnaService;
	private final ResponseHandler responseHandler;
	
	@Value("${file.base-url}")
	private String baseUrl;
	
	//jpa
	@GetMapping("/faqList")
	public List<Faq> getAllFaqs(){
		return faqService.getAllFaqs();
	}
	
	@PostMapping("/createFaq")
	public ResponseEntity<Faq> createFaq(@RequestBody Faq faq) {
		Faq createFaq = faqService.createFaq(faq);
		return ResponseEntity.ok(createFaq);
	}
	
	@PutMapping("/faqUpdate/{faqNo}")
	public ResponseEntity<Faq> updateFaq(@RequestBody Faq faqDetails,
										 @PathVariable("faqNo") int faqNo){
		Faq updateFaq = faqService.updateFaq(faqDetails, faqNo);
		return ResponseEntity.ok(updateFaq);
	}
	
	@DeleteMapping("/faqDelete/{faqNo}")
	public ResponseEntity<Faq> deleteFaq(@PathVariable("faqNo") int faqNo){
		faqService.deleteFaq(faqNo);
		return ResponseEntity.noContent().build(); // 204 no content만 응답
	}
	
	
	//mybatis
	
	//select
	@GetMapping("/qnaList")
	public ResponseEntity<ResponseData> getQnaAll(){
		try {
			List<QnaDto> qnaList = qnaService.qnaFindAll();
			return responseHandler.createResponse("Qna 목록 조회 성공",qnaList, HttpStatus.OK);
		} catch (Exception e) {
			return responseHandler.handleException("조회 실패", e);
		}
	}
	
	
	@GetMapping("/qnaDetail/{qnaNo}")
	public ResponseEntity<ResponseData> getQnaDetail(@PathVariable("qnaNo") int qnaNo){
		try {
			QnaDto qnaDto = qnaService.qnaDetail(qnaNo);
			return responseHandler.createResponse("Qna 조회 성공", qnaDto, HttpStatus.OK);
		} catch (Exception e) {
			return responseHandler.handleException("조회 실패", e);
		}
	}
	
	@GetMapping("/answer/{qnaNo}")
	public ResponseEntity<ResponseData> getAnswer(@PathVariable("qnaNo") int qnaNo){
		try {
			AnswerDto answerDto = qnaService.answerFind(qnaNo);
			return responseHandler.createResponse("답변 조회 성공", answerDto, HttpStatus.OK);
		} catch (Exception e) {
			return responseHandler.handleException("조회 실패", e);
		}
	}
	
	
	//insert
	@PostMapping("/createQna")
	public ResponseEntity<ResponseData> createQna(HttpServletRequest request,
												  @RequestPart("QnaDto") QnaDto qnaDto,
	        									  @RequestPart(value = "files", required = false) List<MultipartFile> files){
		
		try {
			int savedQna  = qnaService.createQna(qnaDto); 
			System.out.println("files: " + files);
			
			String uploadPath = request.getServletContext().getRealPath("/resources/qnaUpload/");
			File directory = new File(uploadPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
				
			if (files != null && !files.isEmpty()) {
	            for (MultipartFile file : files) {
	                String originName = file.getOriginalFilename();
	                String changeName =  System.currentTimeMillis() + "_" + originName;
	                String filePath = baseUrl+"uploads/qna/" + changeName;

	                
	                try {
	                	file.transferTo(new File(directory, changeName));
	                } catch (Exception e) {
	                	log.error("파일 저장 실패 - 경로: {}, 파일명: {}, 이유: {}", directory.getAbsolutePath(), originName, e.getMessage(), e);
	                	throw new RuntimeException("파일 저장 중 오류 발생: " + originName, e);
	                }
                	
                	FileDto fileAdd = new FileDto();
                    fileAdd.setOriginName(originName);
                    fileAdd.setChangeName(changeName);
                    fileAdd.setPath(filePath);
                    
                    try {
                        qnaService.createQnaFile(fileAdd);
                    } catch (Exception e) {
                    	log.error("파일 DB 저장 중 오류 발생: {}, 이유: {}", originName, e.getMessage(), e);
                        throw new RuntimeException("파일 DB 저장 중 오류 발생: " + originName, e);
                    }
	            }
	        }
	        return responseHandler.createResponse("QnA 등록 및 파일 첨부 성공", savedQna , HttpStatus.OK);
	    } catch (Exception e) {
	        return responseHandler.handleException("QnA 등록 실패", e);
	    }
	}
		
	
	@PostMapping("/createAnswer/{qnaNo}")
	public ResponseEntity<ResponseData> createAnswer(HttpServletRequest request,
													 @PathVariable("qnaNo") int qnaNo,
													 @RequestPart("Answer") AnswerDto answer ,
													 @RequestPart(value="files", required = false) List<MultipartFile> files){

		try {
			answer.setQnaNo(qnaNo);
			int answerSave = qnaService.createAnswer(answer);
			System.out.println("files: " + files);
			
			String uploadPath = request.getServletContext().getRealPath("/resources/qnaUpload/");
			File directory = new File(uploadPath);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			
			if (files != null && !files.isEmpty()) {
	            for (MultipartFile file : files) {
	                String originName = file.getOriginalFilename();
	                String changeName =  System.currentTimeMillis() + "_" + originName;
	                String filePath = baseUrl+"uploads/qna/" + changeName;

	                try {
	                	file.transferTo(new File(directory, changeName));
	                } catch (Exception e) {
	                	log.error("파일 저장 실패 - 경로: {}, 파일명: {}, 이유: {}", directory.getAbsolutePath(), originName, e.getMessage(), e);
	                	throw new RuntimeException("파일 저장 중 오류 발생: " + originName, e);
	                }
                	
                	FileDto fileAdd = new FileDto();
                    fileAdd.setOriginName(originName);
                    fileAdd.setChangeName(changeName);
                    fileAdd.setPath(filePath);
                    fileAdd.setQnaNo(qnaNo);
                    
                    try {
                    	qnaService.createAnswerFile(fileAdd);
                    } catch (Exception e) {
                    	log.error("파일 DB 저장 중 오류 발생: {}, 이유: {}", originName, e.getMessage(), e);
                        throw new RuntimeException("파일 DB 저장 중 오류 발생: " + originName, e);
                    }
	            }
	        }
	        return responseHandler.createResponse("답변 등록 및 파일 첨부 성공", answerSave, HttpStatus.OK);
			
		} catch (Exception e) {
			return responseHandler.handleException("답변 등록 실패", e);
		}
	}
	
	
	
	//update
	
	
	
}
