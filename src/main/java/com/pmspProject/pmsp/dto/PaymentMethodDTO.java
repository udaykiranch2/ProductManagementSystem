package com.pmspProject.pmsp.dto;

import lombok.Data;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.UUID;

@Data
public class PaymentMethodDTO {
    private UUID id;
    private String name;
    private String description;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}