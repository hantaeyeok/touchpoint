package com.touchpoint.kh.product.model.service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.touchpoint.kh.product.controller.ProductController;
import com.touchpoint.kh.product.model.dao.ProductMapper;
import com.touchpoint.kh.product.model.dao.ProductRepository;
import com.touchpoint.kh.product.model.vo.Product;
import com.touchpoint.kh.product.model.vo.ProductImage;

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
    public Product save(Product responseProduct) {
        return productRepository.save(responseProduct);
    }
    
    @Transactional
    public void saveProductWithImages(Product product, List<ProductImage> productImages) {
        // 1. 상품 저장
    	productRepository.save(product);

        // 2. 상세 이미지 저장
        for (ProductImage image : productImages) {
            image.setProductId(product.getProductId()); // product에 들어있는 productId를 받아옴
            productRepository.save(image);
        }
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByProductCategory(String category){
        return productRepository.findByProductCategory(category);
    }
    
    
    @Override
    public Product findByProductId(Long productId) {
    	return productRepository.findByProductId(productId);
    }
    @Override
    public List<ProductImage> findImagesByProductId(Long productId) {
    	return productRepository.findImagesByProductId(productId);
    }

	@Override
	public Product deleteById(Long productId) {
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
        
        productRepository.delete(product);
        return product;
    }

	@Transactional
	public void deleteProductWithImages(Long productId, HttpServletRequest request) {
	    Product product = productRepository.findById(productId)
	        .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
	    
	    // 연관된 ProductImage가 존재하는지 확인하고 삭제
	    if (!product.getProductImages().isEmpty()) {
	        //log.info("Deleting associated product images for product ID: {}", productId);
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
	/*
		            // 3. DB에서 데이터 삭제
		            productMapper.removeImg(imageId);
		            log.info("DB에서 이미지 데이터 삭제 완료: ID = {}", imageId);
	*/
		        } catch (Exception e) {
		            log.error("이미지 삭제 중 오류 발생. ID = {}, 에러 = {}", imageId, e.getMessage(), e);
		        }
		    }
	    }
	    productRepository.delete(product);  // Product 삭제
	}

	@Transactional   //트렌젝션 분리
	public void saveProduct(Product product) {
		Logger logger = LoggerFactory.getLogger(getClass());
		long start = System.currentTimeMillis();
		// 1. 상품 정보 저장
	    logger.info("setProduct 실행 전: {}", product);
	    productMapper.setProduct(product);
	    logger.info("setProduct 실행 후: {}", product);
	    
	    long end = System.currentTimeMillis();
	    logger.info("Execution time: {} ms", (end - start));

	}
	
	/*
	@Override
	@Transactional
	public void deleteImages(List<Long> imageIds, HttpServletRequest request) {
	    if (imageIds == null || imageIds.isEmpty()) {
	        log.info("삭제할 이미지가 없습니다.");
	        return;
	    }

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
	*/
	
	@Override
	@Transactional
	public void deleteImages(List<Long> imageIds, HttpServletRequest request) {
	    if (imageIds == null || imageIds.isEmpty()) {
	        log.info("삭제할 이미지가 없습니다.");
	        return;
	    }

	    // 1. 저장 경로 설정
	    String savePath = request.getServletContext().getRealPath("/resources/uploadFiles/");

	    for (Long imageId : imageIds) {
	        try {
	            // 2. DB에서 파일 경로 조회
	            String filePath = productMapper.getImagePathById(imageId);
	            String absolutePath = request.getServletContext().getRealPath(filePath);

	            if (filePath != null ) {
	                // 3. 파일 삭제
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



	@Transactional
	public void updateProductWithImages(Product product, List<ProductImage> productImages, List<Long> deleteImg) {
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
	        image.setProductId(product.getProductId());

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






	public Product update(Product existingProduct) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setProduct(Product product) {
	    productMapper.setProduct(product);
	}


	

	@Override
	public List<ProductImage> deleteImg(List<Long> deleteImg) {
		List<ProductImage> productImages = null;
		for (Long imageId : deleteImg) {
			 productMapper.removeImg(imageId);
	        System.out.println("삭제된 이미지 ID: " + imageId);
	    }
		
		return null;
	}

	
    
	@Override
	public String getPathById(Long imageId) {
		String filePath = productMapper.getImagePathById(imageId);

		return filePath;
	}

	
	
}