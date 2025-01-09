package com.touchpoint.kh.product;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductMapper productMapper;
}

