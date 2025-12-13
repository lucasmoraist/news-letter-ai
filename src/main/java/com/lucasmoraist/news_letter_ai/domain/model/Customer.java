package com.lucasmoraist.news_letter_ai.domain.model;

import java.util.UUID;

public record Customer(
        UUID id,
        String name,
        String email,
        String phoneNumber,
        String gender,
        Boolean isActive
) {}
