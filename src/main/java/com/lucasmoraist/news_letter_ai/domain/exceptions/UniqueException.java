package com.lucasmoraist.news_letter_ai.domain.exceptions;

public class UniqueException extends RuntimeException {

    public UniqueException(String message, Throwable ex) {
        super(message, ex);
    }

}
