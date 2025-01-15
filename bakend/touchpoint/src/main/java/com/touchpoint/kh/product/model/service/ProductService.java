package com.touchpoint.kh.product.model.service;

import com.touchpoint.kh.product.model.vo.Product;
import java.util.List;


public interface ProductService {

    Product save(Product responseProduct);
    List<Product> findAll();
    List<Product> findByProductCategory(String category);
}