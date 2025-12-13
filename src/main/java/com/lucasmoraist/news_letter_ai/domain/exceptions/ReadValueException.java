package com.lucasmoraist.news_letter_ai.domain.exceptions;

public class ReadValueException extends RuntimeException {

    public ReadValueException(String msg, Throwable ex) {
        super(msg, ex);
    }

}
