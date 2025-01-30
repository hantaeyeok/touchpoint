	package com.touchpoint.kh.product.model.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.touchpoint.kh.product.model.vo.Product;
import com.touchpoint.kh.product.model.vo.ProductDto;
import com.touchpoint.kh.product.model.vo.ProductImage;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;


public interface ProductService {

	//ProductDto save(String productJson, MultipartFile upfile, List<MultipartFile> images, HttpServletRequest request);
	Product save(String productJson, MultipartFile upfile, List<MultipartFile> images, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, IOException;

    List<Product> findAll();
    List<Product> findByProductCategory(String category);
	//void saveProductWithImages(Product product, List<ProductImage> productImages);
    Product saveProductWithImages(Product product, List<ProductImage> productImages);
	Product findByProductsId(Long productId);
    Map<String, Object> findByProductId(Long productId);
	List<ProductImage> findImagesByProductId(Long productId);
	Product deleteById(Long productId);
	void deleteProductWithImages(Long productId, HttpServletRequest request);

	void removeAndUpdateImages(Long productId, List<ProductImage> productImages, List<Long> deleteImg);
	//Product update(Product existingProduct);
	//void setProduct(Product product);
	//List<ProductImage> updateImages(Long productId, List<Long> imageIds, List<ProductImage> newImages);
	
	List<ProductImage> deleteImg(List<Long> deleteImg);
	//Product saveProduct(Product updateProduct);
	void deleteImages(List<Long> deleteImg, HttpServletRequest request);
	String getPathById(Long imageId);

	Map<String, Object> updateProduct(Long productId, String productJson, MultipartFile upfile, String deleteImgJson,
			String updateImgJson, List<MultipartFile> images, HttpServletRequest request) throws IOException;


		
	
	
}