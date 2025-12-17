package com.lucasmoraist.news_letter_ai.infrastructure.config;

import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BrevoFeignConfig {

    private static final String API_KEY_HEADER = "api-key";

    @Value("${secrets.brevo.api-key}")
    private String brevoApiKey;

    @Bean
    public RequestInterceptor brevoRequestInterceptor() {
        return requestTemplate -> {
            if (brevoApiKey != null && !brevoApiKey.isEmpty()) {
                requestTemplate.header(API_KEY_HEADER, brevoApiKey);
            }
        };
    }

}
