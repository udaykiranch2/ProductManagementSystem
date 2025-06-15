package com.pmspProject.pmsp.dto.requests;

import java.math.BigDecimal;

import org.hibernate.validator.constraints.UUID;

import lombok.Data;

@Data
public class OrderItemRequest {
    private UUID orderId;
    private UUID productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subtotal;
}
