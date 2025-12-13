package com.lucasmoraist.news_letter_ai.domain.model;

import com.lucasmoraist.news_letter_ai.domain.enums.GenderEnum;

import java.util.UUID;

public record Customer(
        UUID id,
        String name,
        String email,
        String phoneNumber,
        GenderEnum gender,
        Boolean isActive
) {}
