package com.touchpoint.kh.product.model.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.touchpoint.kh.product.controller.ProductController;
//import com.touchpoint.kh.product.controller.ProductConverter;
import com.touchpoint.kh.product.model.dao.ProductMapper;
import com.touchpoint.kh.product.model.dao.ProductRepository;
import com.touchpoint.kh.product.model.vo.Product;
import com.touchpoint.kh.product.model.vo.ProductDto;
import com.touchpoint.kh.product.model.vo.ProductImage;
import com.touchpoint.kh.product.model.vo.ProductResultDto;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    
    @Override
    public ProductResultDto save(String productJson, MultipartFile upfile, List<MultipartFile> images, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, IOException {
    	
	    Product product = new ObjectMapper().readValue(productJson, Product.class);
	    
	    product.setCreatedDate(LocalDateTime.now());
	    
	    log.info("반환받은 upfile :{}" , upfile);
	    
	    product.setThumbnailImage(saveFile(upfile, request));
	    
	    List<ProductImage> productImages = saveImages(images, null, request);
	    
	    product = productRepository.save(product);  //product 저장
	    
	    for (ProductImage image : productImages) {  //productImages 저장
            image.setProductId(product.getProductId()); 
            productRepository.save(image);
        }
		return new ProductResultDto(product, productImages);
    
    }
   
    
    
    //파일 저장
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
	
	//상세 이미지 저장하기 위한 메서드
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
    
	@Override
	@Transactional
	public void deleteProductWithImages(Long productId, HttpServletRequest request) {
	    Product product = productRepository.findById(productId)
	        .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
	    
	    // 연관된 ProductImage가 존재하는지 확인하고 삭제
	    if (!product.getProductImages().isEmpty()) {
	        product.getProductImages().clear();  // 연관된 ProductImage 삭제
	        
	        List<Long> imageIds = productMapper.getImageId(productId);
	        log.info("파일 삭제할 이미지 아이디 : {}", imageIds);
	        

		    for (Long imageId : imageIds) {
		        try {
		            // 1. DB에서 파일 경로 조회
		            String filePath = productMapper.getImagePathById(imageId);
		            String absolutePath = request.getServletContext().getRealPath(filePath);

		            if (filePath != null) {
		                // 2. 파일 삭제
		                File file = new File(absolutePath);
		                if (file.exists()) {
		                    if (file.delete()) {
		                        log.info("파일 삭제 성공: {}", absolutePath);
		                    } else {
		                        log.warn("파일 삭제 실패: {}", absolutePath);
		                    }
		                } else {
		                    log.warn("파일이 존재하지 않음: {}", absolutePath);
		                }
		            }
		        } catch (Exception e) {
		            log.error("이미지 삭제 중 오류 발생. ID = {}, 에러 = {}", imageId, e.getMessage(), e);
		        }
		    }
	    }
	    productRepository.delete(product);  // Product 삭제
	}
	
	//파일삭제
	@Override
	public void deletefiles(List<Long> imageIds, HttpServletRequest request) {
	    if (imageIds == null || imageIds.isEmpty()) {
	        log.info("삭제할 이미지가 없습니다.");
	        return;
	    }
	    String savePath = request.getServletContext().getRealPath("/resources/uploadFiles/");

	    for (Long imageId : imageIds) {
	        try {
	            //DB에서 파일 경로 조회
	            String filePath = productMapper.getImagePathById(imageId);
	            String absolutePath = request.getServletContext().getRealPath(filePath);

	            if (filePath != null ) {
	                //파일 삭제
	                File file = new File(absolutePath);
	                if (file.exists()) {
	                    if (file.delete()) {
	                        log.info("파일 삭제 성공: {}", absolutePath);
	                    } else {
	                        log.warn("파일 삭제 실패: {}", absolutePath);
	                    }
	                } else {
	                    log.warn("파일이 존재하지 않음: {}", absolutePath);

	                }
	                //filePath가 null이면 썸네일인지 검사 
	            } else {  
                    File dir = new File(savePath); 
                    if (dir.exists() && dir.isDirectory()) {
                        File[] files = dir.listFiles((dir1, name) -> name.contains(String.valueOf(imageId)));
                        if (files != null && files.length > 0) {
                            //log.info("이미지 ID {}와 일치하는 파일: {}", imageId, files[0].getAbsolutePath());
                            if (files[0].delete()) {
    	                        log.info("파일 삭제 성공: {}", files[0].getAbsolutePath());
    	                    } else {
    	                        log.warn("파일 삭제 실패: {}", files[0].getAbsolutePath());
    	                    }
                        }
                    }
	            }
	        } catch (Exception e) {
	            log.error("이미지 삭제 중 오류 발생. ID = {}, 에러 = {}", imageId, e.getMessage(), e);
	        }
	    }
	}


	@Override
	@Transactional
	public void removeAndUpdateImages(Long productId, List<ProductImage> productImages, List<Long> deleteImg) {
	    // 1. 삭제된 이미지 처리
	    if (deleteImg != null && !deleteImg.isEmpty()) {
	        for (Long imageId : deleteImg) {
	            try {
	                productMapper.removeImg(imageId);
	                log.info("삭제된 이미지 ID: {}", imageId);
	            } catch (Exception e) {
	                log.error("이미지 삭제 실패: ID = {}, 에러 = {}", imageId, e.getMessage(), e);
	            }
	        }
	    }

	    // 2. 새 이미지 저장 또는 기존 이미지 업데이트
	    for (ProductImage image : productImages) {
	        image.setProductId(productId);

	        if (image.getImageId() == null) { 
	            // 새로 추가된 이미지만 저장
	            productRepository.save(image);
	            log.info("새로운 이미지 저장: {}", image);
	        } else {
	            // 기존 이미지는 업데이트
	            productMapper.updateProductImage(image);
	            log.info("기존 이미지 업데이트: {}", image);
	        }
	    }
	}
    
	
	// 상품 수정 시 상세이미지 수정, 삭제, 추가 메서드
		private List<ProductImage> updateImages(List<MultipartFile> images, List<Long> imageIds, List<Long> deleteImg, List<Long> updateImg , HttpServletRequest request) throws IOException {
		    List<ProductImage> productImages = new ArrayList<>();

		    for (int i = 0; i < images.size(); i++) {
		        MultipartFile image = images.get(i);
		        Long imageId = (imageIds != null && i < imageIds.size()) ? imageIds.get(i) : null;
		        log.info("처리 중인 이미지 ID: {}", imageId);

		        // 1. 삭제된 이미지는 처리에서 제외
		        if (deleteImg != null && deleteImg.contains(imageId)) {
		            log.info("삭제 대상 이미지: {}", imageId);
		            deletefiles(deleteImg, request);  //파일 삭제
		            continue;
		        }

		        // 2. 수정 대상 이미지
		        if (updateImg != null && updateImg.contains(imageId)) {
		            log.info("수정 대상 이미지: {}", imageId);
		            String updatedImagePath = saveFile(image, request); // 새 이미지 저장
		            productImages.add(new ProductImage(updatedImagePath, i, null, imageId)); // 기존 이미지 ID 유지
		            deletefiles(updateImg, request);  //수정 전 파일 삭제
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
		            String existingPath = productMapper.getImagePathById(imageId);
		            productImages.add(new ProductImage(existingPath, i, null, imageId)); // 기존 이미지 추가
		        }
		    }
		    return productImages;
		}
		
	@Override
	public ProductResultDto updateProduct(Long productId, String productJson, MultipartFile upfile, String deleteImgJson, String updateImgJson, List<MultipartFile> images, HttpServletRequest request)  throws IOException  {

		List<Long> deleteImg = new ObjectMapper().readValue(deleteImgJson, new TypeReference<List<Long>>() {});
	    List<Long> updateImg = new ObjectMapper().readValue(updateImgJson, new TypeReference<List<Long>>() {});
		Product updateProduct = new ObjectMapper().readValue(productJson, Product.class); //기본적인 글정보만 넘어옴
		
		//글정보, 썸네일 저장
		if (upfile != null && !upfile.isEmpty()) {
            try {
				updateProduct.setThumbnailImage(saveFile(upfile, request));
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	    productMapper.setProduct(updateProduct);
		
	    //productId로 기존 이미지들 찾기
	    Product product = productRepository.findByProductId(productId);
	    
	    List<ProductImage> productImage = product.getProductImages();  //이미지 id, displayOder 필요함
	    
	    // displayOrder 기준으로 정렬
	    productImage.sort(Comparator.comparingInt(ProductImage::getDisplayOrder));

        // 이미지 ID 리스트 추출 (stream 사용하여 간결하게 처리)
        List<Long> imageIds = productImage.stream()
                                           .map(ProductImage::getImageId)
                                           .collect(Collectors.toList());
	    
	    List<ProductImage> productImages;
		try {
			productImages = updateImages(images, imageIds, deleteImg, updateImg , request);
			log.info("이미지 업데이트한 productImages:{} ", productImages); //삭제된거 제외하고 잘 나옴
			
			removeAndUpdateImages(productId, productImages, deleteImg);  //DB에서 이미지 삭제, 새 이미지 저장하거나 업데이트
			
			Map<String, Object> responseData = new HashMap<>();
		    responseData.put("product", product);
		    responseData.put("images", productImages);
		    
		    return new ProductResultDto(product, productImages);
		    
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}  
	}

	//상품 상세보기
	@Override
    public ProductResultDto findByProductId(Long productId) {
    	Product product = productRepository.findByProductId(productId);
    	List<ProductImage> images = productRepository.findImagesByProductId(productId);
	    
    	return new ProductResultDto(product, images);
    }

	//전체 조회
	@Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

	
	//카테고리별 조회
	@Override
    public List<Product> findByProductCategory(String category){
        return productRepository.findByProductCategory(category);
    }

}