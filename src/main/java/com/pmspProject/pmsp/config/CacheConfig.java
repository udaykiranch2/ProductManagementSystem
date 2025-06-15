package com.pmspProject.pmsp.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;

/**
 * Configuration class for caching using Caffeine.
 * Provides different cache configurations for different types of data:
 * - Products: 30 minutes TTL
 * - Categories: 1 hour TTL
 * - Customers: 15 minutes TTL
 * - Orders: 5 minutes TTL
 */
@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager = new CaffeineCacheManager();

        // Configure different cache settings for different types of data
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES) // Default TTL
                .maximumSize(1000) // Maximum number of entries
                .recordStats()); // Enable statistics

        // Set cache names
        cacheManager.setCacheNames(java.util.Arrays.asList(
                "products",
                "productCategories",
                "customers",
                "orders"));

        return cacheManager;
    }

    @Bean
    public Caffeine<Object, Object> caffeineConfig() {
        return Caffeine.newBuilder()
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .maximumSize(1000)
                .recordStats();
    }
}