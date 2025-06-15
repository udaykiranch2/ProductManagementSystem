package com.pmspProject.pmsp.dto;

import lombok.Data;
import java.time.LocalDateTime;

import org.hibernate.validator.constraints.UUID;

@Data
public class RoleDTO {
    private UUID id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}