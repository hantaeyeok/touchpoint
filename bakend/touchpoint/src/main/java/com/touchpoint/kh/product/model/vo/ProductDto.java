package com.touchpoint.kh.product.model.vo;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ProductDto {
	
	private Long productId; 

    private String productName;

    private String productCategory;

    private String shortDescription;

    private String detailedDescription; 

    private String thumbnailImage;

    private LocalDateTime createdDate;
    
    List<ProductImage> productImages;
    
    private Long imageId;

}
