package com.pmspProject.pmsp.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.UUID;

@Data
public class PaymentDTO {
    private UUID id;
    private UUID orderId;
    private String paymentMethod;
    private BigDecimal amount;
    private String status;
    private String transactionId;
    private LocalDateTime paymentDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}