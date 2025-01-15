package com.touchpoint.kh.product.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.touchpoint.kh.product.model.service.ProductService;
import com.touchpoint.kh.product.model.vo.Product;
import com.touchpoint.kh.product.model.vo.ProductImage;
import com.touchpoint.kh.product.resposnse.ResponseData;

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


	@PostMapping("save")
	public ResponseEntity<ResponseData> save(
										    @RequestParam("product") String productJson, 
										    @RequestParam("upfile") MultipartFile upfile, 
										    @RequestParam("images") List<MultipartFile> images,
										    HttpServletRequest request ) throws IOException {
	    
	    // 상품 데이터 파싱, createDate
	    Product product = new ObjectMapper().readValue(productJson, Product.class);
	    product.setCreatedDate(LocalDateTime.now());
	    
	    // 썸네일 저장
	    product.setThumbnailImage(saveFile(upfile, request));

	    //상세이미지를 저장하는데 필요한 productId얻기 위해 product먼저 저장함
	    Product savedProduct = productService.save(product);
	    
	    // 상세 이미지 저장
	    List<ProductImage> productImages = saveImages(images, request, savedProduct.getProductId());
	    
	    // 저장 처리
	    productService.saveProductWithImages(product, productImages);
	    
	    // 응답 생성 및 반환
	    ResponseData rd = ResponseData.builder()
	                                   .message("상품 추가 성공!")
	                                   .responseData(product)
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
	
	//상세이미지 배열을 저장하기위한 메서드
	private List<ProductImage> saveImages(List<MultipartFile> images, HttpServletRequest request, Long productId) throws IOException {
	    
		List<ProductImage> productImages = new ArrayList<>();
		
		// 인덱스를 가져오는 반복문
	    for (int i = 0; i < images.size(); i++) { 
	        MultipartFile image = images.get(i); // 현재 이미지
	        String imagePath = saveFile(image, request); // 이미지 저장
	        productImages.add(new ProductImage(null, i, imagePath, productId)); // i를 displayOrder로 설정
	    }
	    return productImages;
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
		
		return ResponseEntity.ok(rd);
	}
	
	@GetMapping("/{productId}")
	public ResponseEntity<ResponseData> findByProduct(@PathVariable("productId") Long productId) {

	    // 상품 정보 조회 (예: Service에서 가져오기)
	    Product product = productService.findByProductId(productId);

	    // 상세 이미지 조회 (예: Service에서 가져오기)
	    List<ProductImage> images = productService.findImagesByProductId(productId);

	    // 응답 데이터 생성
	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("product", product);
	    responseData.put("images", images);
	    log.info("반환받은 responseData :{}" , responseData);
	    ResponseData rd = ResponseData.builder()
	                                  .message("상품상세보기 조회 성공!")
	                                  .responseData(responseData)
	                                  .build();

	    return ResponseEntity.ok(rd);
	}


}
