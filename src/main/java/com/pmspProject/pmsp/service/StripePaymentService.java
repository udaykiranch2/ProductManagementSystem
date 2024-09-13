/**
 * StripePaymentService class implements the PaymentGatewayService interface for processing payments using Stripe.
 * It retrieves the Stripe API key from application properties and uses the Stripe Java library to create charges.
 *
 * @author uday
 * @sine 1.0.0.
 */
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

    /**
     * {@inheritDoc}
     *
     * @param amount          the amount to be paid
     * @param paymentMethodId the ID of the payment method (e.g., a token or ID from
     *                        Stripe Elements)
     * @return true if the payment is successful, false otherwise
     * @throws Exception if there is an error during payment processing
     */
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
