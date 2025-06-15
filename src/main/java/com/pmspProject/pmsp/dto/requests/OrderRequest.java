package com.pmspProject.pmsp.dto.requests;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.validator.constraints.UUID;

import lombok.Data;

@Data
public class OrderRequest {
    private UUID customerId;
    private String orderNumber;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal totalAmount;
    private String shippingAddress;
    private String billingAddress;
    private List<OrderItemRequest> orderItems;
    private List<PaymentRequest> payments;
}
