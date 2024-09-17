package com.pmspProject.pmsp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
@Entity
public class CardDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "cardNumber is mandatory")
    private String cardNumber;
    @NotBlank(message = "expiry month is mandatory")
    private String expMonth;
    @NotBlank(message = "expiry year is mandatory")
    private String expYear;
    @NotBlank(message = "cvc is mandatory")
    private String cvc;
}

