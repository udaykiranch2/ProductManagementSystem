package com.pmspProject.pmsp.dto.requests;

import java.util.Set;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private Set<String> roles;
    private boolean enabled;
}
