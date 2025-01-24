package com.touchpoint.kh.product.model.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.touchpoint.kh.product.model.vo.Product;
import com.touchpoint.kh.product.model.vo.ProductImage;

import jakarta.transaction.Transactional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

    List<Product> findByProductCategory(String category);

	void save(ProductImage image);

	Product findByProductId(Long productId);

	@Query("SELECT pi FROM ProductImage pi WHERE pi.productId = :productId ORDER BY pi.displayOrder")
	List<ProductImage> findImagesByProductId(@Param("productId") Long productId);

	
	//void setProduct(Long productId);
	

	//product findById(Long productId);


	

	//List<ProductImage> findImagesByProductId(Long productId);
}