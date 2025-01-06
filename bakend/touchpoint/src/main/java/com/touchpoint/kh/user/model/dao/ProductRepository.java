package com.touchpoint.kh.user.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.touchpoint.kh.user.model.vo.Product;

public interface ProductRepository extends JpaRepository<Product, String> {
	
}
