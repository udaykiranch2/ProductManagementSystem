package com.pmspProject.pmsp.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderRequest {
    private Date orderDate;
    private String status;
    private Double totalAmount;
    private Long customerId;
    private Long productId;
}
