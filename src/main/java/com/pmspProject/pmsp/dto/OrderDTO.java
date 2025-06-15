package com.pmspProject.pmsp.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.validator.constraints.UUID;

@Data
public class OrderDTO {
    private UUID id;
    private UUID customerId;
    private String orderNumber;
    private LocalDateTime orderDate;
    private String status;
    private BigDecimal totalAmount;
    private String shippingAddress;
    private String billingAddress;
    private List<OrderItemDTO> orderItems;
    private List<PaymentDTO> payments;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    public static Object builder() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'builder'");
    }
}