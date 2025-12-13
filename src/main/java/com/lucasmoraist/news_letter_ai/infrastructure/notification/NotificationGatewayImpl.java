package com.lucasmoraist.news_letter_ai.infrastructure.notification;

import com.lucasmoraist.news_letter_ai.application.gateway.NotificationGateway;
import com.lucasmoraist.news_letter_ai.domain.model.Notice;
import com.lucasmoraist.news_letter_ai.infrastructure.genai.GeminiService;
import com.lucasmoraist.news_letter_ai.infrastructure.messages.email.EmailService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Log4j2
@Component
public class NotificationGatewayImpl implements NotificationGateway {

    private final EmailService emailService;
    private final GeminiService geminiService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public NotificationGatewayImpl(EmailService emailService, GeminiService geminiService) {
        this.emailService = emailService;
        this.geminiService = geminiService;
    }

    @Override
    public void sendNotification(String email, List<Notice> notices) {
        String noticesAsString = objectMapper.writeValueAsString(notices);
        String subject = geminiService.generateSubject(noticesAsString);
        this.emailService.sendNotice(email, subject, notices);
    }

}
