package com.pmspProject.pmsp.service;

public interface PaymentGatewayService {
    boolean processPayment(Double amount, String paymentMethodId) throws Exception;
}
