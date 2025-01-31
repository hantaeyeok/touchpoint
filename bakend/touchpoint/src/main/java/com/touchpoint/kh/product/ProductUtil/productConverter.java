package com.touchpoint.kh.product.ProductUtil;

import org.springframework.stereotype.Component;

import com.touchpoint.kh.product.model.vo.Product;
import com.touchpoint.kh.product.model.vo.ProductDto;

//변환용 클래스 따로 만듦
@Component
public class productConverter {
    public Product toEntity(ProductDto dto) {
    	
        return Product.builder()
		                .productId(dto.getProductId())
		                .productName(dto.getProductName())
		                .productCategory(dto.getProductCategory())
		                .shortDescription(dto.getShortDescription())
		                .detailedDescription(dto.getDetailedDescription())
		                .thumbnailImage(dto.getThumbnailImage())
		                .createdDate(dto.getCreatedDate())
		                .build();
    }

    public ProductDto toDto(Product product) {
    	
        return ProductDto.builder()
			                .productId(product.getProductId())
			                .productName(product.getProductName())
			                .productCategory(product.getProductCategory())
			                .shortDescription(product.getShortDescription())
			                .detailedDescription(product.getDetailedDescription())
			                .thumbnailImage(product.getThumbnailImage())
			                .createdDate(product.getCreatedDate())
			                .build();
    }
}
