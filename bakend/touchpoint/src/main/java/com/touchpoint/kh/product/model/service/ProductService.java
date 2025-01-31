package com.touchpoint.kh.product.model.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.touchpoint.kh.product.model.vo.Product;
import com.touchpoint.kh.product.model.vo.ProductDto;
import com.touchpoint.kh.product.model.vo.ProductImage;
import com.touchpoint.kh.product.model.vo.ProductResultDto;

import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;


public interface ProductService {

	ProductResultDto save(String productJson, MultipartFile upfile, List<MultipartFile> images, HttpServletRequest request) throws JsonMappingException, JsonProcessingException, IOException;
    ProductResultDto findByProductId(Long productId);
	void deleteProductWithImages(Long productId, HttpServletRequest request);
	void removeAndUpdateImages(Long productId, List<ProductImage> productImages, List<Long> deleteImg);
	void deletefiles(List<Long> deleteImg, HttpServletRequest request);
	ProductResultDto updateProduct(Long productId, String productJson, MultipartFile upfile, String deleteImgJson, String updateImgJson, List<MultipartFile> images, HttpServletRequest request) throws IOException;
	List<Product> findAll();
	List<Product> findByProductCategory(String category);

	
}