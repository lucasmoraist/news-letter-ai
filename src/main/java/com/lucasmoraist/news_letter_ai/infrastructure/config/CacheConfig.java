package com.lucasmoraist.news_letter_ai.infrastructure.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager cacheManager =
                new CaffeineCacheManager(
                        "gemini-investment-news",
                        "gemini-investment-news-subject",
                        "gemini-investment-news-introduction"
                );

        cacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(23, TimeUnit.HOURS)
                        .maximumSize(10)
        );

        return cacheManager;
    }

}
