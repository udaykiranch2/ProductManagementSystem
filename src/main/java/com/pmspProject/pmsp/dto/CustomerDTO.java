package com.pmspProject.pmsp.dto;

import lombok.Data;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.UUID;

@Data
public class CustomerDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}