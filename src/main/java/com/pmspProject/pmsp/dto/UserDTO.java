package com.pmspProject.pmsp.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

import org.hibernate.validator.constraints.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Set<String> roles;
    private boolean enabled;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}