package com.lucasmoraist.news_letter_ai.domain.enums;

public enum ThemeEnum {
    FINANCE("finanças"),
    GAMERS("gamers"),
    FASHION("moda"),
    POLICY("política"),
    TECHNOLOGY("tecnologia"),
    SPORTS("esportes");

    private final String description;

    public String getDescription() {
        return description;
    }

    ThemeEnum(String description) {
        this.description = description;
    }
}
