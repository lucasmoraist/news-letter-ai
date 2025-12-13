package com.lucasmoraist.news_letter_ai.domain.exceptions;

public class ContentException extends RuntimeException {
    public ContentException(String message, Throwable ex) {
        super(message, ex);
    }
}
