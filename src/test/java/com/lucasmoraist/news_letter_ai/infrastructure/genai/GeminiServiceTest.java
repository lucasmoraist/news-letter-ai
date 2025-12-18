package com.lucasmoraist.news_letter_ai.infrastructure.genai;

import com.google.genai.Client;
import com.google.genai.Models;
import com.google.genai.types.GenerateContentResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GeminiServiceTest {

    @Mock
    Client client;
    @InjectMocks
    GeminiService geminiService;

    @Test
    @DisplayName("Should generate notices using Gemini")
    void case01() {
        Models models = mock(Models.class);
        ReflectionTestUtils.setField(client, "models", models);

        GenerateContentResponse response = mock(GenerateContentResponse.class);
        when(response.text()).thenReturn("generated-notices");
        when(models.generateContent(eq("gemini-2.5-flash"), anyList(), isNull())).thenReturn(response);

        String result = geminiService.generateNotices();

        assertEquals("generated-notices", result);
    }

    @Test
    @DisplayName("Should generate subject using Gemini")
    void case02() {
        Models models = mock(Models.class);
        ReflectionTestUtils.setField(client, "models", models);

        GenerateContentResponse response = mock(GenerateContentResponse.class);
        when(response.text()).thenReturn("Titulo chamativo");
        when(models.generateContent(eq("gemini-2.5-flash"), anyList(), isNull())).thenReturn(response);

        String noticesJson = "[{\"title\":\"exemplo\"}]";
        String result = geminiService.generateSubject(noticesJson);

        assertEquals("Titulo chamativo", result);
    }

    @Test
    @DisplayName("Should generate introduction using Gemini")
    void case03() {
        Models models = mock(Models.class);
        ReflectionTestUtils.setField(client, "models", models);

        GenerateContentResponse response = mock(GenerateContentResponse.class);
        when(response.text()).thenReturn("Introdução curta e envolvente.");
        when(models.generateContent(eq("gemini-2.5-flash"), anyList(), isNull())).thenReturn(response);

        String noticesJson = "[{\"title\":\"exemplo\"}]";
        String result = geminiService.generateIntroduction(noticesJson);

        assertEquals("Introdução curta e envolvente.", result);
    }

}