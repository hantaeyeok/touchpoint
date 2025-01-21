package com.touchpoint.kh.product.controller;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.touchpoint.kh.common.ResponseData;
import com.touchpoint.kh.common.ResponseHandler;
import com.touchpoint.kh.product.model.service.ProductService;
import com.touchpoint.kh.product.model.vo.Product;
import com.touchpoint.kh.product.model.vo.ProductImage;

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

	@PostMapping("save")
	public ResponseEntity<ResponseData> save(
										    @RequestParam("product") String productJson, 
										    @RequestParam("upfile") MultipartFile upfile, 
										    @RequestParam("images") List<MultipartFile> images,
										    HttpServletRequest request ) throws IOException {
	    
	    // 상품 데이터 파싱, createDate
	    Product product = new ObjectMapper().readValue(productJson, Product.class);
	    
	    product.setCreatedDate(LocalDateTime.now());
	    
	    log.info("반환받은 upfile :{}" , upfile);
	    
	    // 썸네일 저장
	    product.setThumbnailImage(saveFile(upfile, request));
	    
	    // 상세 이미지 배열에 저장
	    List<ProductImage> productImages = saveImages(images, null, request);
	    
	    // 트렌젝션으로 저장처리
	    productService.saveProductWithImages(product, productImages);
	    
	    // 응답 생성 및 반환
	    return responseHandler.createResponse("상품추가 성공!", true, HttpStatus.OK);
	}

	
	public String saveFile(MultipartFile upfile, HttpServletRequest request) throws IOException {
	    // getRealPath()로 실제 경로 가져오기
	    String savePath = request.getServletContext().getRealPath("/resources/uploadFiles/");
	    //System.out.println("File Save Path: " + savePath);
	    
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
	
	// 상세 이미지를 저장하기 위한 메서드
	private List<ProductImage> saveImages(List<MultipartFile> images, List<Long> imageIds, HttpServletRequest request) throws IOException {
	    List<ProductImage> productImages = new ArrayList<>();
	    //여기서 처음부터 들어갈 수 있도록 수정해야함
	    // 인덱스를 가져오는 반복문
	    for (int i = 0; i < images.size(); i++) { 
	        MultipartFile image = images.get(i); // 현재 이미지
	        
	        
	        String imagePath = saveFile(image, request); // 이미지 저장
	        log.info("저장할 imagePath :{}" , imagePath);  
	        
	        Long id = (imageIds != null && i < imageIds.size()) ? imageIds.get(i) : null; // imageIds에서 값 가져오기
	        
	        // imageIds가 null이거나 값이 없으면 기본 ProductImage 생성, 아니면 id를 포함하여 생성
	        if (id == null) {
	            productImages.add(new ProductImage(imagePath, i, null, null)); // imageUrl, displayOrder, productId, imageId
	        } else {
	            productImages.add(new ProductImage(imagePath, i, null, id)); // imageUrl, displayOrder, productId, imageId
	        }
	    }
	    return productImages;
	}



	@GetMapping("/category")
	public ResponseEntity<ResponseData> findAll() {
		
		List<Product> all_products = productService.findAll();
		
		log.info("반환받은 product :{}" , all_products);
		
	    return responseHandler.createResponse("전체상품 조회 성공!", all_products, HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<ResponseData> findByProductCategory (@RequestParam("category") String category){
		
		List<Product> productCategory = productService.findByProductCategory(category);
	
		return responseHandler.createResponse("카테고리별 조회 성공!", productCategory, HttpStatus.OK);
	}
	
	
	@GetMapping("/{productId}")
	public ResponseEntity<ResponseData> findByProduct(@PathVariable("productId") Long productId) {

	    // 상품 정보 조회 (예: Service에서 가져오기)
	    Product product = productService.findByProductId(productId);
	    //log.info("반환받은 product :{}" , product); //상세이미지들도 같이 들어옴

	    // 상세 이미지 조회 (예: Service에서 가져오기)
	    List<ProductImage> images = productService.findImagesByProductId(productId);
	    
	    // 응답 데이터 생성
	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("product", product);
	    responseData.put("images", images);
	    //log.info("반환받은 images :{}" , images);

		return responseHandler.createResponse("상품 상세보기 조회 성공!", responseData, HttpStatus.OK);
	}
	
	
	@DeleteMapping("/{productId}")
	public ResponseEntity<ResponseData>deletelById(@PathVariable("productId")Long productId ){
		productService.deleteProductWithImages(productId);
		
		return responseHandler.createResponse("상품 삭제 성공!", null, HttpStatus.OK);

	}
	
	//수정
	@PutMapping("/{productId}")
	public ResponseEntity<ResponseData> update(@PathVariable("productId") Long productId,
																@RequestParam("product") String productJson, 
															    @RequestParam("upfile") MultipartFile upfile, 
															    @RequestParam("images") List<MultipartFile> images,
															    //@RequestParam("imageIds") List<Long> imageIds, 
															    HttpServletRequest request ) throws IOException {
		

		Product updateProduct = new ObjectMapper().readValue(productJson, Product.class);   //수정된 글 읽기
		log.info("updateProduct :{}" , updateProduct);
		
	    Product product = productService.findByProductId(productId);  //imageId받기위해서 갔다옴(수정사항은 반영되지않으니 다른 변수로 받아야함)
	    log.info("product  :{}" , product);   //여기에 순서 제대로 들어옴
	    
	    List<ProductImage> productImage =product.getProductImages(); 
	    List<Long> imageIds = new ArrayList<>(); 
	    
	    for (ProductImage image : productImage) {  //반복문으로 imageIds 배열에 상세이미지 아이디만 담아줌
	        imageIds.add(image.getImageId());
	    };
	    
		// 썸네일 저장 메서드 호출
	    updateProduct.setThumbnailImage(saveFile(upfile, request));
	    
	    // 상세 이미지 저장 메서드 호출
	    List<ProductImage> productImages = saveImages(images, imageIds, request);  //여기 갔다오면 인덱스, 이미지 아이디 배정
	    log.info("아이디 저장 상세이미지  :{}" , productImages);   //새로 들어온 이미지는 id가 null
	    
	    // 트렌젝션으로 저장처리
	    productService.updateProductWithImages(updateProduct, productImages);
	    log.info("저장된 상세이미지 productImages :{}" , productImages);
	    
	    return responseHandler.createResponse("상품 수정 성공!", productImages, HttpStatus.OK);
	}
}
