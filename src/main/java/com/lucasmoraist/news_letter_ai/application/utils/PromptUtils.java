package com.lucasmoraist.news_letter_ai.application.utils;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public final class PromptUtils {

    private PromptUtils() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static String loadPromptTemplate(String pathFile) {
        try (InputStream in = new ClassPathResource(pathFile).getInputStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
