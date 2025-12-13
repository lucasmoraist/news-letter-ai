package com.lucasmoraist.news_letter_ai.infrastructure.config;

import com.google.genai.Client;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GenAIConfig {

    @Value("${secrets.google.genai.api-key}")
    public String genAiApiKey;

    @Bean
    public Client client() {
        return Client.builder()
                .apiKey(genAiApiKey)
                .build();
    }

}
