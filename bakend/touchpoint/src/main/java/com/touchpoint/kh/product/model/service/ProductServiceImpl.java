package com.touchpoint.kh.product.model.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


	@Transactional
	public void updateProductWithImages(Product product, List<ProductImage> productImages) {
		productMapper.setProduct(product);   //상품 정보 저장 
		System.out.println("setProduct: " + product);

        // 2. 상세 이미지 저장
        for (ProductImage image : productImages) {
            image.setProductId(product.getProductId()); // product에 들어있는 productId를 받아옴
            
            if (image.getImageId() == null) {
            	productRepository.save(image);
            	System.out.println("레포지토리 갔다옴: " + image);
            }else {
            	productMapper.updateProductImage(image);
            	System.out.println("매퍼 갔다옴: " + image);
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

	
	
	
}