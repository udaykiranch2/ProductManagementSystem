package com.pmspProject.pmsp.dto.requests;

import lombok.Data;

@Data
public class PaymentMethodRequest {
    private String name;
    private String description;
    private boolean enabled;
}
