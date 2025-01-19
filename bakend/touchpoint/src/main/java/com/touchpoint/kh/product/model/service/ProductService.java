package com.touchpoint.kh.product.model.service;

import com.touchpoint.kh.product.model.vo.Product;
import com.touchpoint.kh.product.model.vo.ProductImage;

import java.util.List;


public interface ProductService {

    Product save(Product responseProduct);
    List<Product> findAll();
    List<Product> findByProductCategory(String category);
	void saveProductWithImages(Product product, List<ProductImage> productImages);
	Product findByProductId(Long productId);
	List<ProductImage> findImagesByProductId(Long productId);
	Product deleteById(Long productId);
	void deleteProductWithImages(Long productId);

	void updateProductWithImages(Product product, List<ProductImage> productImages);
}