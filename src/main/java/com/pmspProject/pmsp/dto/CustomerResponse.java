package com.pmspProject.pmsp.dto;

import java.util.List;

import lombok.Data;

@Data
public class CustomerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String address;
    private List<OrderResponse> orders;
}
