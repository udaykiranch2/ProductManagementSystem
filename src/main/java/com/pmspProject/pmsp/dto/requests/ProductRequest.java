package com.pmspProject.pmsp.dto.requests;

import java.math.BigDecimal;
import java.util.UUID;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private UUID categoryId;
    private String categoryName;
    private String sku;
    private String imageUrl;
}
