/**
 * Controller for handling Stripe-related operations.
 *
 * @author uday
 * @since 1.0.0
 */
package com.pmspProject.pmsp.controller;

import com.stripe.model.Token;
import com.stripe.param.TokenCreateParams;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StripeController {
    /**
     * Endpoint for generating a Stripe payment method token for testing purposes.
     *
     * <p>
     * This method creates a token for a Visa test card using the Stripe API. The
     * generated token can be used
     * for testing payment processing in your application.
     *
     * <p>
     * The method uses the following test card details:
     * <ul>
     * <li>Card Number: 4242424242424242</li>
     * <li>Expiration Month: 12</li>
     * <li>Expiration Year: 2024</li>
     * <li>CVC: 123</li>
     * </ul>
     *
     * <p>
     * The generated token can be used as the {@code paymentMethodId} in your
     * application to test the payment processing.
     *
     * @return a string indicating the success or failure of token generation
     */
    @GetMapping("/api/generateToken")
    public String generateVisaToken() {
        try {
            // Creating card parameters for testing
            TokenCreateParams.Card cardParams = TokenCreateParams.Card.builder()
                    .setNumber("4242424242424242") // Visa test card
                    .setExpMonth("12")
                    .setExpYear("2024")
                    .setCvc("123")
                    .build();

            // Creating token parameters with the card
            TokenCreateParams params = TokenCreateParams.builder()
                    .setCard(cardParams)
                    .build();

            // Generate the token using Stripe API
            Token token = Token.create(params);

            return "Generated Token: " + token.getId();
        } catch (Exception e) {
            return "Error creating token: " + e.getMessage();
        }
    }
}
