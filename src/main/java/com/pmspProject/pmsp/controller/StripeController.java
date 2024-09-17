/**
 * Controller for handling Stripe-related operations.
 *
 * @author uday
 * @since 1.0.0
 */
package com.pmspProject.pmsp.controller;

import com.pmspProject.pmsp.model.CardDetails;
import com.stripe.Stripe;
import com.stripe.model.Token;
import com.stripe.param.TokenCreateParams;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class StripeController {
    @Value("${stripe.api.key}")
    private String stripeApiKey;

    private CardDetails cardDetails;

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
    public ResponseEntity<String> generateToken(@Valid @RequestBody Map<String, String> cardDetails) {
        // Initialize Stripe with the API key
        Stripe.apiKey = stripeApiKey;
        try {
            // Extract card details from the JSON object
            String cardNumber = cardDetails.get("cardNumber");
            String expMonth = cardDetails.get("expMonth");
            String expYear = cardDetails.get("expYear");
            String cvc = cardDetails.get("cvc");
            // Creating card parameters for testing
            TokenCreateParams.Card cardParams = TokenCreateParams.Card.builder()
                    .setNumber(cardNumber) // Accepted as a parameter
                    .setExpMonth(expMonth) // Accepted as a parameter
                    .setExpYear(expYear) // Accepted as a parameter
                    .setCvc(cvc) // Accepted as a parameter
                    .build();

            // Creating token parameters with the card
            TokenCreateParams params = TokenCreateParams.builder()
                    .setCard(cardParams)
                    .build();

            // Generate the token using Stripe API
            Token token = Token.create(params);

            return ResponseEntity.ok("Generated Token: " + token.getId());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating token: " + e.getMessage());
        }
    }
}