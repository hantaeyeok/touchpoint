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
    private Long productId; 

    private String productName;

    private String productCategory;

    private String shortDescription;

    private String detailedDescription; 

    private String thumbnailImage;

    private LocalDateTime createdDate;
}