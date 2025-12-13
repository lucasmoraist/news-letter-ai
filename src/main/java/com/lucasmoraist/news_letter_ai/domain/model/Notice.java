package com.lucasmoraist.news_letter_ai.domain.model;

public record Notice(
        String title,
        String content,
        String originalUrl,
        String publicationDate,
        String createdAt
) {

}
