package com.lucasmoraist.news_letter_ai.domain.exceptions;

public class EmailException extends RuntimeException {
    public EmailException(String msg, Throwable ex) {
        super(msg, ex);
    }
}
