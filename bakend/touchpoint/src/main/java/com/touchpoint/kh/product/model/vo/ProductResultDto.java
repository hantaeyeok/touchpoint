package com.touchpoint.kh.product.model.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductResultDto {
	private Product product;
    private List<ProductImage> productImages;

}
