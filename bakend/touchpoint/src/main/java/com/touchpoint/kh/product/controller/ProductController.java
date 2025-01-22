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
	    
	    Product product = new ObjectMapper().readValue(productJson, Product.class);
	    
	    product.setCreatedDate(LocalDateTime.now());
	    
	    log.info("반환받은 upfile :{}" , upfile);
	    
	    product.setThumbnailImage(saveFile(upfile, request));
	    
	    List<ProductImage> productImages = saveImages(images, null, null, request);
	    
	    productService.saveProductWithImages(product, productImages);
	    
	    return responseHandler.createResponse("상품추가 성공!", true, HttpStatus.OK);
	}

	
	public String saveFile(MultipartFile upfile, HttpServletRequest request) throws IOException {
		
	    String savePath = request.getServletContext().getRealPath("/resources/uploadFiles/");
	    
	    if (savePath == null) {
	        savePath = "/path/to/your/upload/directory/";  // 사용자의 디렉터리 경로로 수정
	    }
	    File directory = new File(savePath);
	    
	    if (!directory.exists()) {
	        directory.mkdirs();  
	    }
	    
	    String fileName = System.currentTimeMillis() + "_" + upfile.getOriginalFilename();
	    File file = new File(savePath + fileName);
	    upfile.transferTo(file);
	    
	    return "/resources/uploadFiles/" + fileName;  
	}
	
	// 상세 이미지를 저장하기 위한 메서드
	private List<ProductImage> saveImages(List<MultipartFile> images, List<Long> imageIds, List<Long> deleteImg, HttpServletRequest request) throws IOException {
	    List<ProductImage> productImages = new ArrayList<>();

	    for (int i = 0; i < images.size(); i++) { 
	        MultipartFile image = images.get(i); // 현재 이미지
	        
	        String imagePath = saveFile(image, request); // 이미지 저장
	        log.info("저장할 imagePath :{}" , imagePath);  

	        Long id = (imageIds != null && i < imageIds.size()) ? imageIds.get(i) : null; // imageIds에서 값 가져오기
	        
	        //id가 값이 없거나(처음등록) deleteImg에 포함된 id라면(삭제된이미지)  imageId를 배정하지 않음
	        if (id == null || deleteImg==null || deleteImg.contains(id)) {    
	            productImages.add(new ProductImage(imagePath, i, null, null)); // ID 배정하지 않음
	        } else {
	            productImages.add(new ProductImage(imagePath, i, null, id)); // 기존 ID 배정
	        }
	    }log.info("저장할 imagePath :{}"  );  
	    return productImages;
	}




	@GetMapping("/category")
	public ResponseEntity<ResponseData> findAll() {
		
		List<Product> all_products = productService.findAll();
		//log.info("반환받은 product :{}" , all_products);
		
	    return responseHandler.createResponse("전체상품 조회 성공!", all_products, HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<ResponseData> findByProductCategory (@RequestParam("category") String category){
		
		List<Product> productCategory = productService.findByProductCategory(category);
	
		return responseHandler.createResponse("카테고리별 조회 성공!", productCategory, HttpStatus.OK);
	}
	
	
	@GetMapping("/{productId}")
	public ResponseEntity<ResponseData> findByProduct(@PathVariable("productId") Long productId) {

	    Product product = productService.findByProductId(productId);

	    List<ProductImage> images = productService.findImagesByProductId(productId);
	    
	    Map<String, Object> responseData = new HashMap<>();
	    responseData.put("product", product);
	    responseData.put("images", images);

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
															    @RequestParam("deleteImg") String deleteImgJson,
															    @RequestParam("images") List<MultipartFile> images,
															    HttpServletRequest request ) throws IOException {

	    List<Long> deleteImg = new ObjectMapper().readValue(deleteImgJson, new TypeReference<List<Long>>() {});


		Product updateProduct = new ObjectMapper().readValue(productJson, Product.class); 
		
	    Product product = productService.findByProductId(productId);  //imageId받기위해서 갔다옴(수정사항은 반영되지않으니 다른 변수로 받아야함)
	    
	    List<ProductImage> productImage =product.getProductImages(); 
	    List<Long> imageIds = new ArrayList<>(); 
	    
	    for (ProductImage image : productImage) {  //반복문으로 imageIds 배열에 상세이미지 아이디만 담아줌
	        imageIds.add(image.getImageId());
	    };
	    
	    updateProduct.setThumbnailImage(saveFile(upfile, request));
	    
	    List<ProductImage> productImages = saveImages(images, imageIds, deleteImg, request);  //여기 갔다오면 인덱스, 이미지 아이디 배정
	    log.info("실행 확인용 로그"  );  
	    
	    productService.saveProduct(updateProduct);
	    
	    productService.updateProductWithImages(updateProduct, productImages, deleteImg);
	    log.info("반환받은 updateProduct :{}" , updateProduct);
	    log.info("반환받은 productImages :{}" , productImages);
	    
	    return responseHandler.createResponse("상품 수정 성공!", productImages, HttpStatus.OK);
	}
}
