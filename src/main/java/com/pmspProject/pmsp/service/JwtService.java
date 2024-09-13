/**
 * This class represents the JwtService, which provides methods for generating and validating JSON Web Tokens (JWTs).
 *
 * The JwtService class uses the io.jsonwebtoken library to perform JWT operations.
 *
 * @author uday
 * @since 1.0
 */
package com.pmspProject.pmsp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private String secretkey = generateSecretKey();

    public JwtService() {
    }

    /**
     * Generates a secret key for JWT encryption.
     *
     * @return The generated secret key.
     */
    public String generateSecretKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            SecretKey secretKey = keyGen.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error generating secret key ", e);
        }
    }

    /**
     * Generates a JWT token for the given username.
     *
     * @param username The username for which the JWT token will be generated.
     * @return The generated JWT token.
     */
    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 10))
                .signWith(getKey(), Jwts.SIG.HS256)
                .compact();
    }

    /**
     * Retrieves the secret key for JWT encryption.
     *
     * @return The secret key.
     */
    private SecretKey getKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretkey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token The JWT token from which the username will be extracted.
     * @return The extracted username.
     */
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a claim from the given JWT token.
     *
     * @param token         The JWT token from which the claim will be extracted.
     * @param claimResolver A function that resolves the claim from the JWT token.
     * @return The extracted claim.
     */
    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    /**
     * Extracts all claims from the given JWT token.
     *
     * @param token The JWT token from which the claims will be extracted.
     * @return The extracted claims.
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Validates the given JWT token for the given user details.
     *
     * @param token       The JWT token to be validated.
     * @param userDetails The user details for which the JWT token will be
     *                    validated.
     * @return True if the token is valid, otherwise false.
     */
    public boolean validateToken(String token, UserDetails userDetails) {

        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Checks if the given JWT token has expired.
     *
     * @param token The JWT token to be checked.
     * @return True if the token has expired, otherwise false.
     */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from the given JWT token.
     *
     * @param token The JWT token from which the expiration date will be extracted.
     * @return The extracted expiration date.
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
