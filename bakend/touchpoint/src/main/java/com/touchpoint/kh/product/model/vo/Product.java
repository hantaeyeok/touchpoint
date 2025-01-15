package com.touchpoint.kh.product.model.vo;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
    @Column(name = "PRODUCT_ID")
    private Long productId; // NUMBER 타입 매핑

    @Column(name = "PRODUCT_NAME", nullable = false)
    private String productName;

    @Column(name = "PRODUCT_CATEGORY", nullable = false)
    private String productCategory;

    @Column(name = "SHORT_DESCRIPTION", nullable = false)
    private String shortDescription;

    @Column(name = "DETAILED_DESCRIPTION")
    private String detailedDescription; 

    @Column(name = "THUMBNAIL_IMAGE", nullable = false)
    private String thumbnailImage;

    @Column(name = "CREATED_DATE", nullable = false)
    private LocalDateTime createdDate;
}
