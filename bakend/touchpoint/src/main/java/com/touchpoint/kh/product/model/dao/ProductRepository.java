package com.touchpoint.kh.product.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.touchpoint.kh.product.model.vo.Product;

public interface ProductRepository extends JpaRepository<Product, String>{

	List<Product> findByProductCategory(String category);
}
