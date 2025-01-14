package com.touchpoint.kh.product.model.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.touchpoint.kh.product.model.vo.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{

	List<Product> findByProductCategory(String category);
}
