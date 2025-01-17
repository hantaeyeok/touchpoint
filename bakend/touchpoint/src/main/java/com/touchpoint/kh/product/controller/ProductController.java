package com.touchpoint.kh.product.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.touchpoint.kh.product.model.service.ProductService;
import com.touchpoint.kh.product.model.vo.Product;
import com.touchpoint.kh.product.resposnse.ResponseData;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;

/*
	@PostMapping
	public ResponseEntity<ResponseData> save(@RequestBody String product) throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException {

	    log.info("앞단에서 받은 데이터:{}" , product);

	    // URL 디코딩 후 처리
	    String decodedProduct = URLDecoder.decode(product, "UTF-8");

	    Product responseProduct = new ObjectMapper().readValue(decodedProduct, Product.class);
	    responseProduct.setCreatedDate(LocalDateTime.now());
	    log.info("responseProduct:{}" , responseProduct);
	    Product saveObj = productService.save(responseProduct);

	    ResponseData rd = ResponseData.builder()
	                                  .message("상품 추가 성공!")
	                                  .responseData(saveObj)
	                                  .build();

	    log.info("반환받은 Product :{}" , saveObj);

	    return ResponseEntity.ok(rd);
	}
	*/
	
	
	@PostMapping("save")
	public ResponseEntity<ResponseData> save(@RequestParam("product") String productJson, 
	                                          @RequestParam("upfile") MultipartFile upfile, 
	                                          HttpServletRequest request) throws IOException {
	    log.info("받은 상품 데이터: {}", productJson);
	    
	    // 1. JSON 데이터를 객체로 변환
	    Product product = new ObjectMapper().readValue(productJson, Product.class);
	    
	    // 2. 파일 처리 (파일 경로 저장)
	    String filePath = saveFile(upfile, request);
	    product.setThumbnailImage(filePath);  // 파일 경로 저장

	    // 3. 데이터베이스 저장 및 응답 반환
	    Product savedProduct = productService.save(product);
	    ResponseData rd = ResponseData.builder()
	                                   .message("상품 추가 성공!")
	                                   .responseData(savedProduct)
	                                   .build();
	    return ResponseEntity.ok(rd);
	}

	public String saveFile(MultipartFile upfile, HttpServletRequest request) throws IOException {
	    // getRealPath()로 실제 경로 가져오기
	    String savePath = request.getServletContext().getRealPath("/resources/uploadFiles/");
	    
	    // 만약 getRealPath()가 null을 반환하면 다른 경로를 사용
	    if (savePath == null) {
	        savePath = "/path/to/your/upload/directory/";  // 사용자의 디렉터리 경로로 수정
	    }
	    
	    File directory = new File(savePath);
	    
	    // 디렉터리가 존재하지 않으면 생성
	    if (!directory.exists()) {
	        directory.mkdirs();  // 디렉터리 생성
	    }
	    
	    // 파일명 생성 (현재 시간을 기반으로 파일명 생성)
	    String fileName = System.currentTimeMillis() + "_" + upfile.getOriginalFilename();
	    
	    // 파일 저장
	    File file = new File(savePath + fileName);
	    upfile.transferTo(file);
	    
	    return "/resources/uploadFiles/" + fileName;  // 저장된 파일 경로 반환
	}





	
	@GetMapping("/category")
	public ResponseEntity<ResponseData> findAll() {
		
		List<Product> all_products = productService.findAll();
		
		ResponseData rd = ResponseData.builder()
									  .message("전체상품 조회 성공!")
									  .responseData(all_products)
									  .build();
		
		log.info("반환받은 product :{}" , all_products);
		
		return ResponseEntity.ok(rd);
	}
	
	@GetMapping()
	public ResponseEntity<ResponseData> findByProductCategory (@RequestParam("category") String category){
		
		List<Product> productCategory = productService.findByProductCategory(category);
		
		ResponseData rd = ResponseData.builder()
									  .message("카테고리별 조회 성공!")
									  .responseData(productCategory)
									  .build();
		
		//log.info("카데고리별 product :{}" , productCategory);
		
		return ResponseEntity.ok(rd);
	}
	
	
}
