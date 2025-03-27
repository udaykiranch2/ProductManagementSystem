package com.pmspProject.pmsp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderResponse {
    private Long id;
    private Date orderDate;
    private String status;
    private Double totalAmount;
    private CustomerResponse customer;
    private ProductResponse product;
}
