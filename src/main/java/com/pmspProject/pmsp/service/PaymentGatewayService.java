/**
 * PaymentGatewayService interface defines the contract for processing payments.
 * Implementations of this interface should handle specific payment gateways (e.g., Stripe, PayPal).
 *
 * @author uday
 * @since 1.0.0
 */
package com.pmspProject.pmsp.service;

public interface PaymentGatewayService {
    /**
     * Processes a payment using the specified payment method.
     *
     * @param amount          the amount to be paid
     * @param paymentMethodId the ID of the payment method
     * @return true if the payment is successful, false otherwise
     * @throws Exception if there is an error during payment processing
     */
    boolean processPayment(Double amount, String paymentMethodId) throws Exception;
}
