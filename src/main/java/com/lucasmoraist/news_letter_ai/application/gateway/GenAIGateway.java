package com.lucasmoraist.news_letter_ai.application.gateway;

public interface GenAIGateway {
    String sendPromptToGemini(String prompt);
}
