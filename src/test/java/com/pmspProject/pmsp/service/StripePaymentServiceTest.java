package com.pmspProject.pmsp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

@ExtendWith(MockitoExtension.class)
public class StripePaymentServiceTest {

    @Mock
    private PaymentGatewayService stripePaymentService;

    @Captor
    private ArgumentCaptor<Map<String, Object>> chargeArgumentsCaptor;

    @BeforeEach
    public void setUp() {
        stripePaymentService = new StripePaymentService();
    }

    @Test
    public void testProcessPayment_Success() throws Exception {
        // Given
        Double amount = 10.0;
        String paymentMethodId = "pm_card_1234567890";

        // Mock Stripe API call
        OngoingStubbing<Charge> chargeStubbing = when(Charge.create(anyMap())).thenReturn(new Charge());
        chargeStubbing.thenReturn(new Charge());

        // When
        boolean paymentSuccessful = stripePaymentService.processPayment(amount, paymentMethodId);

        // Then
        assertTrue(paymentSuccessful, "Payment should be successful");
        Charge.create(chargeArgumentsCaptor.capture());
        Map<String, Object> chargeArguments = chargeArgumentsCaptor.getValue();
        assertEquals(Math.round(amount * 100), chargeArguments.get("amount"), "Amount should match");
        assertEquals("usd", chargeArguments.get("currency"), "Currency should match");
        assertEquals(paymentMethodId, chargeArguments.get("source"), "Payment method ID should match");
        assertEquals("Order Payment", chargeArguments.get("description"), "Description should match");
    }

    @Test
    public void testProcessPayment_Failure() throws Exception {
        // Given
        Double amount = 10.0;
        String paymentMethodId = "pm_card_1234567890";

        // Mock Stripe API call
        when(Charge.create(anyMap())).thenThrow(new Exception("Payment processing failed", null));

        // When
        Exception exception = assertThrows(Exception.class,
                () -> stripePaymentService.processPayment(amount, paymentMethodId));

        // Then
        assertEquals("Payment processing failed", exception.getMessage(), "Exception message should match");
    }
}
