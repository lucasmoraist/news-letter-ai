package com.lucasmoraist.news_letter_ai.infrastructure.genai;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import com.lucasmoraist.news_letter_ai.application.gateway.GenAIGateway;
import lombok.extern.log4j.Log4j2;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Log4j2
@Service
public class GeminiService implements GenAIGateway {

    private final Client client;

    public GeminiService(Client client) {
        this.client = client;
    }

    @Retryable(
            delay = 10000,
            maxRetries = 5
    )
    @Override
    public String sendPromptToGemini(String prompt) {
        log.debug("Preparing request to Gemini model...");

        Content content = Content.builder()
                .role("user")
                .parts(Collections.singletonList(Part.fromText(prompt)))
                .build();
        log.debug("Sending request to Gemini model...");

        GenerateContentResponse response = client.models
                .generateContent(
                        "gemini-2.5-flash",
                        Collections.singletonList(content),
                        null
                );
        log.debug("Received response from Gemini model.");

        return response.text();
    }

}
