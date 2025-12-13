package com.lucasmoraist.news_letter_ai.infrastructure.schedule;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasmoraist.news_letter_ai.application.usecases.notification.SendNotification;
import com.lucasmoraist.news_letter_ai.domain.exceptions.ContentException;
import com.lucasmoraist.news_letter_ai.domain.model.Notice;
import com.lucasmoraist.news_letter_ai.infrastructure.genai.GeminiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class DailySchedule {

    private final GeminiService geminiService;
    private final SendNotification sendNotification;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Scheduled(cron = "0 0 8 * * ?", zone = "America/Sao_Paulo")
    public void execute() {
        log.info("Initiating daily newsletter generation process...");

        String generatedContent = geminiService.generateNotices()
                .replace("```json", "")
                .replace("```", "")
                .trim();

        try {
            List<Notice> notices = objectMapper.readValue(generatedContent, new TypeReference<>() {});
            this.sendNotification.execute(notices);
        } catch (JsonProcessingException e) {
            log.error("Error parsing generated content: {}", e.getMessage());
            throw new ContentException("Failed to parse generated content into notices.", e);
        }
    }

}
