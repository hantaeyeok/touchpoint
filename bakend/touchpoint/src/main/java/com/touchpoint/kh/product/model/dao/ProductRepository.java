package com.touchpoint.kh.product.model.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.touchpoint.kh.product.model.vo.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

	List<Product> findByProductCategory(String category);
	
	
}
