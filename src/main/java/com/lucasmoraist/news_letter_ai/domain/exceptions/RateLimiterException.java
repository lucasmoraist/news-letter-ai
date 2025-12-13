package com.lucasmoraist.news_letter_ai.domain.exceptions;

public class RateLimiterException extends RuntimeException {

    public RateLimiterException(String message, Throwable ex) {
        super(message, ex);
    }

}
