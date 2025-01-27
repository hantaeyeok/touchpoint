package com.touchpoint.kh.qna.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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
import org.springframework.web.bind.annotation.RequestParam;
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
    public ResponseEntity<ResponseData> getQnaAll(@RequestParam(name = "page") int page,
    											  @RequestParam(name = "size") int size) {
        try {
            Map<String, Object> qnaList = qnaService.qnaFindAllWithPaging(page, size);
            return responseHandler.createResponse("Qna 목록 조회 성공", qnaList, HttpStatus.OK);
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
	private List<FileDto> saveFiles(HttpServletRequest request, 
									List<MultipartFile> files, 
									int qnaNo,
									int answerNo) {

	    String uploadPath = request.getServletContext().getRealPath("/resources/qnaUpload/");
	    File directory = new File(uploadPath);
	    if (!directory.exists()) {
	        directory.mkdirs();
	    }

	    List<FileDto> fileDtos = new ArrayList<>();
	    for (MultipartFile file : files) {
	        String originName = file.getOriginalFilename();
	        String changeName = System.currentTimeMillis() + "_" + originName;
	        String filePath = baseUrl + changeName;

	        try {
	            file.transferTo(new File(directory, changeName));
	        } catch (Exception e) {
	            log.error("파일 저장 실패 - 경로: {}, 파일명: {}, 이유: {}", directory.getAbsolutePath(), originName, e.getMessage(), e);
	            throw new RuntimeException("파일 저장 중 오류 발생: " + originName, e);
	        }

	        FileDto fileDto = new FileDto();
	        fileDto.setOriginName(originName);
	        fileDto.setChangeName(changeName);
	        fileDto.setPath(filePath);
	        fileDto.setQnaNo(qnaNo);
	        fileDto.setAnswerNo(answerNo);
	        fileDtos.add(fileDto);
	    }
	    return fileDtos;
	}
	
	@PostMapping("/createQna")
	public ResponseEntity<ResponseData> createQna(HttpServletRequest request,
												  @RequestPart("QnaDto") QnaDto qnaDto,
	        									  @RequestPart(value = "files", required = false) List<MultipartFile> files){
		
		Integer number = 0; //매개변수 개수 맞추기위함..
		try {
	        int savedQna = qnaService.createQna(qnaDto);
	        
	        if (files == null || files.isEmpty()) {
	            log.warn("첨부 파일이 없습니다.");
	            return responseHandler.createResponse("QnA 등록 성공 (첨부 파일 없음)", savedQna, HttpStatus.OK);
	        }
	        
	        List<FileDto> fileDtos = saveFiles(request, files, savedQna, number);

	        for (FileDto fileDto : fileDtos) {
	            try {
	                qnaService.createQnaFile(fileDto);
	            } catch (Exception e) {
	                log.error("파일 DB 저장 중 오류 발생: {}, 이유: {}", fileDto.getOriginName(), e.getMessage(), e);
	                throw new RuntimeException("파일 DB 저장 중 오류 발생: " + fileDto.getOriginName(), e);
	            }
	        }
	        return responseHandler.createResponse("QnA 등록 및 파일 첨부 성공", savedQna, HttpStatus.OK);
	    } catch (Exception e) {
	        return responseHandler.handleException("QnA 등록 실패", e);
	    }
	}
		
	
	@PostMapping("/createAnswer/{qnaNo}")
	public ResponseEntity<ResponseData> createAnswer(HttpServletRequest request,
													 @PathVariable("qnaNo") int qnaNo,
													 @RequestPart("Answer") AnswerDto answerDto ,
													 @RequestPart(value="files", required = false) List<MultipartFile> files){
		
		Integer number = 0; //매개변수 개수 맞추기위함..
		try {
	        answerDto.setQnaNo(qnaNo);
	        int answerSave = qnaService.createAnswer(answerDto);
	        
	        if (files == null || files.isEmpty()) {
	            log.warn("첨부 파일이 없습니다.");
	            return responseHandler.createResponse("Answer 등록 성공 (첨부 파일 없음)", answerSave, HttpStatus.OK);
	        }
	        
	        List<FileDto> fileDtos = saveFiles(request, files, qnaNo, number);

	        for (FileDto fileDto : fileDtos) {
	            try {
	                qnaService.createAnswerFile(fileDto);
	            } catch (Exception e) {
	                log.error("파일 DB 저장 중 오류 발생: {}, 이유: {}", fileDto.getOriginName(), e.getMessage(), e);
	                throw new RuntimeException("파일 DB 저장 중 오류 발생: " + fileDto.getOriginName(), e);
	            }
	        }
	        return responseHandler.createResponse("답변 등록 및 파일 첨부 성공", answerSave, HttpStatus.OK);
	    } catch (Exception e) {
	        return responseHandler.handleException("답변 등록 실패", e);
	    }
	}
	
	
	
	//update
	private List<FileDto> updateFile(HttpServletRequest request,
									 List<MultipartFile> files,
									 List<FileDto> prevFiles){
		
			Integer qnaNo = prevFiles.get(0).getQnaNo();
			Integer answerNo = prevFiles.get(0).getAnswerNo();
			System.out.println("prevFiles : " + prevFiles);
			System.out.println("qnaNo : " + qnaNo);
			System.out.println("answerNo : " + answerNo);
			// 새로 추가된 파일 필터링 (조건 필터링)
			List<MultipartFile> newFilesToSave = files.stream()
												.filter(file -> prevFiles.stream()
												.noneMatch(prev -> file.getOriginalFilename().equals(prev.getOriginName())))
												.collect(Collectors.toList());
			
			// 새로 추가된 파일만 저장
			List<FileDto> newFiles = saveFiles(request, newFilesToSave, qnaNo, answerNo);
			for (FileDto fileDto : newFiles) {
				try {
					qnaService.insNewFile(fileDto);
				} catch (Exception e) {
					log.error("파일 DB 저장 중 오류 발생: {}, 이유: {}", fileDto.getOriginName(), e.getMessage(), e);
					throw new RuntimeException("파일 DB 저장 중 오류 발생: " + fileDto.getOriginName(), e);
				}
			}
			
			//기존 파일 삭제
	        for (FileDto existingFile : prevFiles) {
	            try {
	                // 겹치는 파일이 있는지 확인 (조건없이 일치하지 않는것만)
	                boolean isDeleted = files.stream()
	                				   .noneMatch(file -> file.getOriginalFilename().equals(existingFile.getOriginName()));

	                if (isDeleted) {
	                    System.out.println("File to delete: " + existingFile.getOriginName());
	                    File oldFile = new File(request.getServletContext().getRealPath("/resources/qnaUpload/"), existingFile.getChangeName());
	                    if (oldFile.exists()) {
	                        oldFile.delete();
	                        System.out.println("Deleting physical file: " + oldFile.getAbsolutePath());
	                    }
	                    qnaService.deleteFile(existingFile.getFileNo());
	                } else {
	                    System.out.println("File retained: " + existingFile.getOriginName());
	                }
	            } catch (Exception e) {
	                System.out.println("Error processing file: " + existingFile.getOriginName());
	                e.printStackTrace();
	            }
	        }
        return newFiles;
	}
	
	private List<FileDto> deletePrevFile(HttpServletRequest request,
										 List<FileDto> prevFiles){
		List<FileDto> deletedFiles = new ArrayList<>();
		for (FileDto existingFile : prevFiles) {
            try {
                File oldFile = new File(request.getServletContext().getRealPath("/resources/qnaUpload/"), existingFile.getChangeName());
                if (oldFile.exists()) {
                    oldFile.delete();
                    System.out.println("Deleting physical file: " + oldFile.getAbsolutePath());
                }
                deletedFiles.add(existingFile);
                qnaService.deleteFile(existingFile.getFileNo());
            } catch (Exception e) {
                System.out.println("Error processing file: " + existingFile.getOriginName());
                e.printStackTrace();
            }
        }
		return deletedFiles;
	}
	
	@PutMapping("/updateQna/{qnaNo}")
	public ResponseEntity<ResponseData> updateQna(HttpServletRequest request,
	                                              @PathVariable("qnaNo") int qnaNo,
	                                              @RequestPart("QnaDto") QnaDto qnaDto,
	                                              @RequestPart(value = "files", required = false) List<MultipartFile> files) {
	    try {
	        qnaService.updateQna(qnaDto);
	        QnaDto prevFile = qnaService.qnaDetail(qnaNo);
	        List<FileDto> prevFiles = prevFile.getFiles();
	        
	        if (files == null || files.isEmpty()) {
	            log.warn("첨부 파일이 없습니다.");
	            List<FileDto> deletePrevFiles = deletePrevFile(request, prevFiles);
	            return responseHandler.createResponse("QnA 등록 성공 (첨부 파일 없음)", deletePrevFiles, HttpStatus.OK);
	        }
	        
	        List<FileDto> updatedFiles = updateFile(request, files, prevFiles);
	        
	        return responseHandler.createResponse("QnA 업데이트 및 파일 첨부 성공", updatedFiles, HttpStatus.OK);
	    } catch (Exception e) {
	        return responseHandler.handleException("QnA 업데이트 실패", e);
	    }
	}
	
	@PutMapping("/updateAnswer/{qnaNo}")
	public ResponseEntity<ResponseData> updateAnswer(HttpServletRequest request,
													 @PathVariable("qnaNo") int qnaNo,
													 @RequestPart("AnswerDto") AnswerDto answerDto,
													 @RequestPart(value ="files", required = false) List<MultipartFile> files){
		try {
			qnaService.updateAnswer(answerDto);
			AnswerDto prevFile = qnaService.answerFind(qnaNo);
			List<FileDto> prevFiles = prevFile.getFiles();
			
			if (files == null || files.isEmpty()) {
	            log.warn("첨부 파일이 없습니다.");
	            List<FileDto> deletePrevFiles = deletePrevFile(request, prevFiles);
	            return responseHandler.createResponse("Answer 등록 성공 (첨부 파일 없음)", deletePrevFiles, HttpStatus.OK);
	        }
			
			List<FileDto> updatedFiles = updateFile(request, files, prevFiles);
			
			return responseHandler.createResponse("QnA 업데이트 및 파일 첨부 성공", updatedFiles, HttpStatus.OK);
	    } catch (Exception e) {
	        return responseHandler.handleException("QnA 업데이트 실패", e);
	    }
	}
	
	//down
	@GetMapping("/download/{changeName}")
	public ResponseEntity<Resource> downloadFile(@PathVariable("changeName") String fileName,
			      								 HttpServletRequest request) {
	    // 파일 저장 경로 
	    String filePath = request.getServletContext().getRealPath("/resources/qnaUpload/"+ fileName); 
	    File file = new File(filePath);

	    if (!file.exists()) {
	        throw new RuntimeException("파일이 존재하지 않습니다: " + fileName);
	    }

	    // 파일 리소스 생성
	    Resource resource = new FileSystemResource(file);

	    // 파일 다운로드 강제 설정
	    HttpHeaders headers = new HttpHeaders();
	    headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream"); // Content-Type 설정
	    headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileName + "\""); // 다운로드 강제

	    // HTTP 응답 생성
	    return ResponseEntity.ok()
	            .headers(headers)
	            .body(resource);
	}
	
	//delete
	@PostMapping("/qnaDelete/{qnaNo}")
	public ResponseEntity<ResponseData> qnaDelete(@PathVariable("qnaNo") int qnaNo,
												  @RequestBody QnaDto qnaDto,
												  HttpServletRequest request){
		try {
			QnaDto prevFile = qnaService.qnaDetail(qnaNo);
			List<FileDto> prevFiles = prevFile.getFiles();
			if(prevFiles != null && !prevFiles.isEmpty()) {
				deletePrevFile(request, prevFiles);
			}
			int deleteQna = qnaService.deleteQna(qnaNo); 
			return responseHandler.createResponse("QnA 삭제 성공", deleteQna, HttpStatus.OK);
	    } catch (Exception e) {
	        return responseHandler.handleException("QnA 삭제 실패", e);
	    }										  
	}
	
	@PostMapping("/answerDelete/{qnaNo}")
	public ResponseEntity<ResponseData> answerDelete(@PathVariable("qnaNo") int qnaNo,
													 @RequestBody AnswerDto answerDto,
													 HttpServletRequest request){
		try {
			AnswerDto prevFile = qnaService.answerFind(qnaNo);
			List<FileDto> prevFiles = prevFile.getFiles();
			if(prevFiles != null && !prevFiles.isEmpty()) {
				deletePrevFile(request, prevFiles);
			}
			int deleteAnswer = qnaService.deleteAnswer(qnaNo); 
			return responseHandler.createResponse("QnA 삭제 성공", deleteAnswer, HttpStatus.OK);
		} catch (Exception e) {
			return responseHandler.handleException("QnA 삭제 실패", e);
		}										  
	}
}
