package com.touchpoint.kh.product.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.touchpoint.kh.common.ResponseData;
import com.touchpoint.kh.common.ResponseHandler;
import com.touchpoint.kh.product.model.service.ProductService;
import com.touchpoint.kh.product.model.vo.Product;
import com.touchpoint.kh.product.model.vo.ProductImage;
import com.touchpoint.kh.product.model.vo.ProductResultDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/product")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class ProductController {
	private final ProductService productService;
	private final ResponseHandler responseHandler;

	
	//상품 추가
	@PostMapping("/admin/save")
	public ResponseEntity<ResponseData> save(@RequestParam("product") String productJson, 
										    @RequestParam("upfile") MultipartFile upfile, 
										    @RequestParam("images") List<MultipartFile> images, HttpServletRequest request ) throws IOException {
		try {
			ProductResultDto responseData = productService.save(productJson, upfile, images, request);
	        return responseHandler.createResponse("상품 추가 성공!", responseData, HttpStatus.OK);
	    } catch (Exception e) {
	        return responseHandler.handleException("상품 추가 실패", e);
	    }
	}
	
	//전체상품 조회
	@GetMapping("/category")
	public ResponseEntity<ResponseData> findAll() {
	    try {
	    	List<Product> all_products = productService.findAll();
	        return responseHandler.createResponse("전체 상품 조회 성공!", all_products, HttpStatus.OK);
	    } catch (Exception e) {
	        return responseHandler.handleException("전체상품 조회 실패", e);
	    }
	}
	
	//카테고리별 조회
	@GetMapping()
	public ResponseEntity<ResponseData> findByProductCategory (@RequestParam("category") String category){
		try {
			List<Product> productCategory = productService.findByProductCategory(category);
	    	return responseHandler.createResponse("카테고리별 조회 성공!", productCategory, HttpStatus.OK);	    
	    } catch (Exception e) {
	        return responseHandler.handleException("카테고리별 조회 실패", e);
	    }
	}
	
	//상품 상세보기
	@GetMapping("/{productId}")
	public ResponseEntity<ResponseData> findByProductId(@PathVariable("productId") Long productId) {
		try {
			ProductResultDto responseData = productService.findByProductId(productId);
			return responseHandler.createResponse("상품 상세 조회 성공!", responseData, HttpStatus.OK);
	    } catch (Exception e) {
	        return responseHandler.handleException("상품 상세 조회 실패", e);
	    }
	}
	
	//상품 삭제
	@DeleteMapping("/admin/{productId}")
	public ResponseEntity<ResponseData>deletelById(@PathVariable("productId")Long productId, HttpServletRequest request ){
		try {
			productService.deleteProductWithImages(productId, request);
			return responseHandler.createResponse("상품 삭제 성공!", true, HttpStatus.OK);
	    } catch (Exception e) {
	        return responseHandler.handleException("상품 삭제 실패", e);
	    }
	}
	
	//수정
	@PutMapping("/admin/{productId}")
	public ResponseEntity<ResponseData> update(@PathVariable("productId") Long productId,
																@RequestParam("product") String productJson, 
															    @RequestParam("upfile") MultipartFile upfile, 
															    @RequestParam("deleteImg") String deleteImgJson,
															    @RequestParam("updateImg") String updateImgJson,
															    @RequestParam("images") List<MultipartFile> images, HttpServletRequest request ) throws IOException {
	    try {
	    	ProductResultDto responseData = productService.updateProduct(productId, productJson, upfile, deleteImgJson, updateImgJson, images, request);
		    return responseHandler.createResponse("상품 수정 성공!", responseData, HttpStatus.OK);
	    } catch (Exception e) {
	        return responseHandler.handleException("상품 수정 실패", e);
	    }
	}
}
