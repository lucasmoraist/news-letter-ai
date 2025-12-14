package com.lucasmoraist.news_letter_ai.infrastructure.genai;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.ClassPathResource;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;

@Log4j2
@Service
@Retryable(
        delay = 10000,
        maxRetries = 5
)
public class GeminiService {

    private final Client client;

    public GeminiService(Client client) {
        this.client = client;
    }

    @Cacheable(
            value = "gemini-investment-news",
            key = "'daily-news'"
    )
    public String generateNotices() {
        final String prompt = loadPromptTemplate("prompts/finance-prompt.txt");

        GenerateContentResponse response = sendRequestToGemini(prompt);

        return response.text();
    }


    @Cacheable(
            value = "gemini-investment-news-subject",
            key = "'daily-news'"
    )
    public String generateSubject(String notices) {
        final String prompt = """            
            Com base nas noticias a seguir crie **apenas 1 título** chamativo e intrigante para adicionar no título do email 
            
            - Retorne como String
            - Não inclua nenhuma explicação, prefácio ou pontuação extra.
            
            Notícias em JSON:
            {{NOTICES}}
            """;

        final String promptWithNotices = prompt.replace("{{NOTICES}}", notices);

        GenerateContentResponse response = sendRequestToGemini(promptWithNotices);

        return response.text();
    }

    @Cacheable(
            value = "gemini-investment-news-introduction",
            key = "'daily-news'"
    )
    public String generateIntroduction(String notices) {
        final String prompt = """            
            Com base nas noticias a seguir crie uma introdução curta e envolvente para o email de newsletter sobre investimentos.
            
            - A introdução deve ser persuasiva e incentivar a leitura do restante do conteúdo.
            - Utilize uma linguagem acessível e amigável, adequada para um público interessado em investimentos.
            - Mantenha a introdução entre 2 a 3 frases curtas.
            - Não inclua nenhuma explicação, prefácio ou pontuação extra.
            
            Notícias em JSON:
            {{NOTICES}}
            """;

        final String promptWithNotices = prompt.replace("{{NOTICES}}", notices);

        GenerateContentResponse response = sendRequestToGemini(promptWithNotices);

        return response.text();
    }

    private GenerateContentResponse sendRequestToGemini(String prompt) {
        log.debug("Preparing request to Gemini model...");

        Content content = Content.builder()
                .role("user")
                .parts(Collections.singletonList(Part.fromText(prompt)))
                .build();
        log.debug("Sending request to Gemini model...");

        GenerateContentResponse response = client.models
                .generateContent(
                        "gemini-2.5-flash",
                        Collections.singletonList(content),
                        null
                );
        log.debug("Received response from Gemini model.");

        return response;
    }

    private String loadPromptTemplate(String pathFile) {
        try (InputStream in = new ClassPathResource(pathFile).getInputStream()) {
            return new String(in.readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
