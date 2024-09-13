package com.pmspProject.pmsp.service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StripePaymentService implements PaymentGatewayService {

    @Value("${stripe.api.key}")
    private String stripeApiKey;

    @Override
    public boolean processPayment(Double amount, String paymentMethodId) throws Exception {
        // Initialize Stripe with the API key
        Stripe.apiKey = stripeApiKey;

        // Create a charge
        try {
            Charge charge = Charge.create(Map.of(
                    "amount", Math.round(amount * 100), // Convert amount to cents
                    "currency", "usd",
                    "source", paymentMethodId, // Payment method ID (e.g., a token or ID from Stripe Elements)
                    "description", "Order Payment"));

            // Check if the charge was successful
            return "succeeded".equals(charge.getStatus());
        } catch (StripeException e) {
            // Handle exceptions related to payment processing
            throw new Exception("Payment processing failed", e);
        }
    }
}
