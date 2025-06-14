package com.pmspProject.pmsp.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;

@Component
public class TokenUtil {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    public String extractUsername(String token) {
        // Remove "Bearer " prefix
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        // Decode the secret key from Base64
        Key signingKey = getSigningKey();

        // Parse the token using the new method
        Claims claims = Jwts.parser()
                .setSigningKey(signingKey) // Use the decoded Key here
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // Assuming the username is stored in 'sub'
    }

    private Key getSigningKey() {
        // Ensure this is only decoded once if possible for performance
        // or ensure it's handled safely if it needs to be decoded per request
        return Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }
}
