package com.touchpoint.kh.product.model.vo;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TB_PRODUCT")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long productId; 

    private String productName;

    private String productCategory;

    private String shortDescription;

    private String detailedDescription; 

    private String thumbnailImage;

    private LocalDateTime createdDate;
    
    //private List<ProductImage> productImages;
}