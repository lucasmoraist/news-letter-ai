package com.lucasmoraist.news_letter_ai.infrastructure.genai;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.resilience.annotation.Retryable;
import org.springframework.stereotype.Service;

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
        final String prompt = """
            Busque exatamente 5 notícias completas, publicadas no dia anterior, relacionadas ao tema investimentos (mercado financeiro, renda fixa, renda variável, economia, juros, bolsa de valores, fundos de investimento, CDI, SELIC, Tesouro Direto, etc.).
            
            As notícias devem ser completas e bem desenvolvidas, com contexto, impacto no mercado e informações suficientes para informar assinantes, evitando resumos superficiais.
            
            Retorne o resultado obrigatoriamente em formato de lista, onde cada item da lista deve conter exatamente os seguintes campos:
            
            title: título da notícia
            content: noticia
            originalUrl: URL original da fonte da notícia
            publicationDate: data de publicação no formato yyyy-MM-dd
            createdAt: data e hora da geração do registro no formato yyyy-MM-dd'T'HH:mm:ss
            
            ⚠️ Regras importantes:
            
            - Retorne exatamente 5 itens na lista
            - Não inclua campos extras
            - Não inclua explicações, comentários ou textos fora da lista
            - Não utilize placeholders ou textos genéricos
            - As notícias devem ser reais e verificáveis
            
            📌 Formato esperado (exemplo):
            [
              {
                "title": "Título da notícia",
                "content": "Resumo da notícia...",
                "originalUrl": "https://exemplo.com/noticia",
                "publicationDate": "2025-12-12",
                "createdAt": "2025-12-13T08:00:00"
              }
            ]
            """;

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

}
