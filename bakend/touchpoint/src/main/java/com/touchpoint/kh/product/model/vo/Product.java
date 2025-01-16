package com.touchpoint.kh.product.model.vo;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
	
	//product테이블의 id가 비워지면 productImages테이블의 엔티티도 자동으로 비워줌
    @OneToMany(mappedBy = "productId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductImage> productImages;

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