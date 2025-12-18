package com.lucasmoraist.news_letter_ai.domain.model;

import java.util.UUID;

public record Theme(
        UUID id,
        String name,
        Customer customer
) {

}
