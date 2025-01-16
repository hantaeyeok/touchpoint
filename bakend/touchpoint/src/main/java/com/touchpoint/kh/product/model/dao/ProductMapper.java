package com.touchpoint.kh.product.model.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import com.touchpoint.kh.product.model.vo.ProductImage;

@Mapper
public interface ProductMapper {
	List<ProductImage> findImagesByProductId(@Param("productId") Long productId);

}
