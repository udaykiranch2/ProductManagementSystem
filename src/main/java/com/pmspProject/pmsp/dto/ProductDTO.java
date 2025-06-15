package com.pmspProject.pmsp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import org.hibernate.validator.constraints.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private UUID categoryId;
    private String categoryName;
    private String sku;
    private String imageUrl;
}