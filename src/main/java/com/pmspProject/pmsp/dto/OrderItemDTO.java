package com.pmspProject.pmsp.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.UUID;

@Data
public class OrderItemDTO {
    private UUID id;
    private UUID orderId;
    private UUID productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}