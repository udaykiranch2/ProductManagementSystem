package com.pmspProject.pmsp.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Configuration class for rate limiting using Bucket4j.
 * Provides different rate limits for different types of endpoints:
 * - Authentication endpoints: 5 requests per minute
 * - API endpoints: 100 requests per minute
 * - Public endpoints: 200 requests per minute
 */
@Configuration
public class RateLimitConfig {

    private static final Map<String, Bucket> BUCKETS = new ConcurrentHashMap<>();

    @Bean
    public Map<String, Bucket> createBuckets() {
        // Authentication endpoints bucket (5 requests per minute)
        Bandwidth authLimit = Bandwidth.simple(5, Duration.ofMinutes(1));
        Bucket authBucket = Bucket4j.builder()
                .addLimit(authLimit)
                .build();
        BUCKETS.put("auth", authBucket);

        // API endpoints bucket (100 requests per minute)
        Bandwidth apiLimit = Bandwidth.simple(100, Duration.ofMinutes(1));
        Bucket apiBucket = Bucket4j.builder()
                .addLimit(apiLimit)
                .build();
        BUCKETS.put("api", apiBucket);

        // Public endpoints bucket (200 requests per minute)
        Bandwidth publicLimit = Bandwidth.simple(200, Duration.ofMinutes(1));
        Bucket publicBucket = Bucket4j.builder()
                .addLimit(publicLimit)
                .build();
        BUCKETS.put("public", publicBucket);

        return BUCKETS;
    }

    public static Bucket getBucket(String type) {
        return BUCKETS.getOrDefault(type, BUCKETS.get("api")); // Default to API bucket if type not found
    }
}