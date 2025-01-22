package com.touchpoint.kh.product.model.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.touchpoint.kh.product.model.dao.ProductMapper;
import com.touchpoint.kh.product.model.dao.ProductRepository;
import com.touchpoint.kh.product.model.vo.Product;
import com.touchpoint.kh.product.model.vo.ProductImage;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

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
	public void deleteProductWithImages(Long productId) {
	    Product product = productRepository.findById(productId)
	        .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + productId));
	    
	    // 연관된 ProductImage가 존재하는지 확인하고 삭제
	    if (!product.getProductImages().isEmpty()) {
	        //log.info("Deleting associated product images for product ID: {}", productId);
	        product.getProductImages().clear();  // 연관된 ProductImage 삭제
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

	@Transactional
	public void updateProductWithImages(Product product, List<ProductImage> productImages, List<Long> deleteImg) {
		
		long start = System.currentTimeMillis();
		
	    Logger logger = LoggerFactory.getLogger(getClass());
/*
	    // 1. 상품 정보 저장
	    logger.info("setProduct 실행 전: {}", product);
	    productMapper.setProduct(product);
	    logger.info("setProduct 실행 후: {}", product);
*/
	    
	    // 2. 삭제된 이미지 DB에서 삭제
	    if (deleteImg != null && !deleteImg.isEmpty()) {
	        for (Long imageId : deleteImg) {
	            try {
	                productMapper.removeImg(imageId);
	                logger.info("삭제된 이미지 ID: {}", imageId);
	            } catch (Exception e) {
	                logger.error("이미지 삭제 실패: ID = {}, 에러 = {}", imageId, e.getMessage(), e);
	            }
	        }
	    }else {
	        logger.info("No images to delete.");
	    }


	    // 3. 상세 이미지 저장
	    for (ProductImage image : productImages) {
	        image.setProductId(product.getProductId()); // productId 설정

	        // 이미지 ID가 null인경우 새로운 이미지로 저장
	        if (image.getImageId() == null) {
	            productRepository.save(image);  
	            logger.info("새로운 이미지 저장: {}", image);
	        } else {
	            productMapper.updateProductImage(image); 
	            logger.info("기존 이미지 업데이트: {}", image);
	        }
	    }
	    
	    long end = System.currentTimeMillis();
	    logger.info("Execution time: {} ms", (end - start));
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


	
	
}