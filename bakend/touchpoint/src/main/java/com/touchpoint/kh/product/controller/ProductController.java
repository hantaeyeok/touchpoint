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
	    
		
		long start = System.currentTimeMillis();
		
	    Product product = new ObjectMapper().readValue(productJson, Product.class);
	    
	    product.setCreatedDate(LocalDateTime.now());
	    
	    log.info("반환받은 upfile :{}" , upfile);
	    
	    product.setThumbnailImage(saveFile(upfile, request));
	    
	    List<ProductImage> productImages = saveImages(images, null, request);
	    
	    productService.saveProductWithImages(product, productImages);
	    
	    long end = System.currentTimeMillis();
	    log.info("등록 처리하는데 걸린 시간 : {} ms", (end - start));
	    
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
	private List<ProductImage> saveImages(List<MultipartFile> images, List<Long> imageIds, HttpServletRequest request) throws IOException {
	    List<ProductImage> productImages = new ArrayList<>();

	    log.info("images.size():{} ", images.size());
	    
	    for (int i = 0; i < images.size(); i++) {
	        MultipartFile image = images.get(i); // 현재 이미지
	        
	        log.info("앞단에서 넘어온 image:{} ", image);
	        
	        Long imageId = (imageIds != null && i < imageIds.size()) ? imageIds.get(i) : null; // 기존 이미지 ID 가져오기

	        
	        // 새로운 이미지는 저장
	        if (image != null && !image.isEmpty()) {
	            String imagePath = saveFile(image, request); // 새 이미지 저장
	            log.info("새로운 이미지 저장: {}", imagePath);

	            productImages.add(new ProductImage(imagePath, i, null, null));
	        }
	        
	    }
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
	public ResponseEntity<ResponseData>deletelById(@PathVariable("productId")Long productId, HttpServletRequest request ){
		productService.deleteProductWithImages(productId, request);
		//productService.deleteImages(productId);
		
		return responseHandler.createResponse("상품 삭제 성공!", null, HttpStatus.OK);
	}
	
	// 상세이미지 수정, 삭제, 추가 메서드
	private List<ProductImage> updateImages(List<MultipartFile> images, List<Long> imageIds, List<Long> deleteImg, List<Long> updateImg, HttpServletRequest request) throws IOException {
	    List<ProductImage> productImages = new ArrayList<>();

	    for (int i = 0; i < images.size(); i++) {
	        MultipartFile image = images.get(i);
	        Long imageId = (imageIds != null && i < imageIds.size()) ? imageIds.get(i) : null;
	        log.info("처리 중인 이미지 ID: {}", imageId);

	        // 1. 삭제된 이미지는 처리에서 제외
	        if (deleteImg != null && deleteImg.contains(imageId)) {
	            log.info("삭제 대상 이미지: {}", imageId);
	            continue;
	        }

	        // 2. 수정 대상 이미지
	        if (updateImg != null && updateImg.contains(imageId)) {
	            log.info("수정 대상 이미지: {}", imageId);
	            String updatedImagePath = saveFile(image, request); // 새 이미지 저장
	            productImages.add(new ProductImage(updatedImagePath, i, null, imageId)); // 기존 이미지 ID 유지
	            continue;
	        }

	        // 3. 새 이미지 추가
	        if (imageId == null) {
	            log.info("새로운 이미지 추가");
	            String newImagePath = saveFile(image, request); // 새 이미지 저장
	            productImages.add(new ProductImage(newImagePath, i, null, null)); // 새로운 이미지 추가
	        } else {
	            // 4. 기존 이미지를 유지
	            log.info("기존 이미지 재사용: {}", imageId);
	            String existingPath = productService.getPathById(imageId);
	            productImages.add(new ProductImage(existingPath, i, null, imageId)); // 기존 이미지 추가
	        }
	    }

	    return productImages;
	}

	
	
	



	
	//수정
	@PutMapping("/{productId}")
	public ResponseEntity<ResponseData> update(@PathVariable("productId") Long productId,
																@RequestParam("product") String productJson, 
															    @RequestParam("upfile") MultipartFile upfile, 
															    @RequestParam("deleteImg") String deleteImgJson,
															    @RequestParam("updateImg") String updateImgJson,
															    @RequestParam("images") List<MultipartFile> images,
															    HttpServletRequest request ) throws IOException {

		long start = System.currentTimeMillis();
		
	    log.info(" 제일처음 앞단에서 넘어온 images:{} ", images); //멀티파일 나옴..

		
	    List<Long> deleteImg = new ObjectMapper().readValue(deleteImgJson, new TypeReference<List<Long>>() {});
	    List<Long> updateImg = new ObjectMapper().readValue(updateImgJson, new TypeReference<List<Long>>() {});

		Product updateProduct = new ObjectMapper().readValue(productJson, Product.class); 
		
	    Product product = productService.findByProductId(productId);  //imageId받기위해서 갔다옴(수정사항은 반영되지않으니 다른 변수로 받아야함)
	   
	    
	    List<ProductImage> productImage = product.getProductImages();
	    
	 // displayOrder를 기준으로 정렬
	    productImage = productImage.stream()
	            .sorted(Comparator.comparingInt(ProductImage::getDisplayOrder))
	            .collect(Collectors.toList());

	    log.info("정렬된 이미지 아이디 잘 나옴 ? productImage : {}", productImage);  //순서 잘 안지켜짐
	    List<Long> imageIds = new ArrayList<>();

	    // 삭제한 이미지를 제외하고 imageIds에 추가
	    for (ProductImage image : productImage) {
	        Long imageId = image.getImageId();
	        if (deleteImg == null || !deleteImg.contains(imageId) || !updateImg.contains(imageId)) {
	            imageIds.add(imageId); // 삭제된 이미지가 아닌 경우에만 추가
	        }
	    }
	    
	    log.info("삭제된 이미지를 제외한 이미지 아이디: {}", imageIds);
	    
	    updateProduct.setThumbnailImage(saveFile(upfile, request));
	    
	    List<ProductImage> productImages = updateImages(images, imageIds, deleteImg, updateImg,  request);  //여기 갔다오면 인덱스, 이미지 아이디 배정
	
	    
	    
	    log.info("앞단에서 넘어온 productImages:{} ", productImages); //여기까지 삭제된거 제외하고 잘 나옴 - 마지막 이미지 아이디 null
	    
	    productService.saveProduct(updateProduct);
	    
	    productService.deleteImages(deleteImg, request);
	    
	    productService.updateProductWithImages(updateProduct, productImages, deleteImg);
	    
	    long end = System.currentTimeMillis();
	    log.info("수정 처리하는데 걸린 시간 : {} ms", (end - start));
	    
	    return responseHandler.createResponse("상품 수정 성공!", productImages, HttpStatus.OK);
	}
}
