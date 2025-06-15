package com.pmspProject.pmsp.dto.requests;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.UUID;

import lombok.Data;

@Data
public class PaymentRequest {
    private UUID orderId;
    private String paymentMethod;
    private BigDecimal amount;
    private String status;
    private String transactionId;
    private LocalDateTime paymentDate; 
}
