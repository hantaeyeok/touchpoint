package com.touchpoint.kh.user.model.service;

import org.springframework.stereotype.Service;

import com.touchpoint.kh.user.model.dao.ProductMapper;
import com.touchpoint.kh.user.model.dao.ProductRepository;
import com.touchpoint.kh.user.model.vo.Product;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductMapper productMapper;
	private final ProductRepository productRepository;
	
	@Override
	public Product save(Product product) {
		return productRepository.save(product);
	}
}

