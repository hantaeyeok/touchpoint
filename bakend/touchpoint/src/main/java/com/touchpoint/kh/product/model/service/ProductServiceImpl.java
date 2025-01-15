package com.touchpoint.kh.product.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.touchpoint.kh.product.model.dao.ProductRepository;
import com.touchpoint.kh.product.model.vo.Product;
import com.touchpoint.kh.product.model.vo.ProductImage;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product save(Product responseProduct) {
        return productRepository.save(responseProduct);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByProductCategory(String category){
        return productRepository.findByProductCategory(category);
    }
    
    @Transactional
    public void saveProductWithImages(Product product, List<ProductImage> productImages) {
        // 1. 상품 저장
        productRepository.save(product);

        // 2. 상세 이미지 저장
        for (ProductImage image : productImages) {
            //image.setProductId(product.getProductId()); // 외래 키 설정
            productRepository.save(image);
        }
    }
    @Override
    public Product findByProductId(Long productId) {
    	return productRepository.findByProductId(productId);
    }
    @Override
    public List<ProductImage> findImagesByProductId(Long productId) {
    	return productRepository.findImagesByProductId(productId);
    }
}