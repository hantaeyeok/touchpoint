package com.touchpoint.kh.product.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.touchpoint.kh.product.model.service.ProductService;
import com.touchpoint.kh.product.model.vo.Product;
import com.touchpoint.kh.product.resposnse.ResponseData;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;


	@PostMapping
	public ResponseEntity<ResponseData> save(@RequestBody String product) throws JsonMappingException, JsonProcessingException{
	
		log.info("앞단에서 받은 데이터:{}" , product);
		
		Product responseProduct = new ObjectMapper().readValue(product, Product.class);
		responseProduct.setCreatedDate(LocalDateTime.now());
		
		Product saveObj = productService.save(responseProduct);
		
		ResponseData rd = ResponseData.builder()
									  .message("상품 추가 성공!")
									  .responseData(saveObj)
									  .build();
		
		log.info("반환받은 Produc :{}" , saveObj);

		return ResponseEntity.ok(rd);
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
