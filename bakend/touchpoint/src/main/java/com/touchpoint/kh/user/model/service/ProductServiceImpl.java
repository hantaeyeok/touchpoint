package com.touchpoint.kh.user.model.service;

import org.springframework.stereotype.Service;

import com.touchpoint.kh.user.model.dao.ProductMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
	private final ProductMapper productMapper;
}
