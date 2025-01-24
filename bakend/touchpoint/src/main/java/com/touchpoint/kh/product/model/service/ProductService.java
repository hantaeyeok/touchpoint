	package com.touchpoint.kh.product.model.service;

import com.touchpoint.kh.product.model.vo.Product;
import com.touchpoint.kh.product.model.vo.ProductImage;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;


public interface ProductService {

    Product save(Product responseProduct);
    List<Product> findAll();
    List<Product> findByProductCategory(String category);
	void saveProductWithImages(Product product, List<ProductImage> productImages);
	Product findByProductId(Long productId);
	List<ProductImage> findImagesByProductId(Long productId);
	Product deleteById(Long productId);
	void deleteProductWithImages(Long productId, HttpServletRequest request);

	void updateProductWithImages(Product product, List<ProductImage> productImages, List<Long> deleteImg);
	Product update(Product existingProduct);
	void setProduct(Product product);
	//List<ProductImage> updateImages(Long productId, List<Long> imageIds, List<ProductImage> newImages);
	
	List<ProductImage> deleteImg(List<Long> deleteImg);
	void saveProduct(Product updateProduct);
	void deleteImages(List<Long> deleteImg, HttpServletRequest request);
	String getPathById(Long imageId);
	
}