package com.lucasmoraist.news_letter_ai.infrastructure.messages.gateway;

import com.lucasmoraist.news_letter_ai.domain.model.Notice;
import com.lucasmoraist.news_letter_ai.infrastructure.genai.GeminiService;
import com.lucasmoraist.news_letter_ai.infrastructure.messages.email.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationGatewayImplTest {

    @Mock
    GeminiService geminiService;

    @Mock
    EmailService emailService;

    @Test
    @DisplayName("Should build subject and introduction and send email")
    void case01() {
        NotificationGatewayImpl gateway = new NotificationGatewayImpl(geminiService, emailService);

        Notice notice = mock(Notice.class);
        List<Notice> notices = List.of(notice);

        when(geminiService.generateSubject(anyString())).thenReturn("Assunto gerado");
        when(geminiService.generateIntroduction(anyString())).thenReturn("Introdução gerada");

        gateway.sendNotification("a@example.com", notices);

        verify(geminiService).generateSubject(anyString());
        verify(geminiService).generateIntroduction(anyString());

        ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);
        verify(emailService).sendEmail(eq("a@example.com"), eq("Assunto gerado"), bodyCaptor.capture());
        assertTrue(bodyCaptor.getValue().contains("Introdução gerada"));
    }

    @Test
    @DisplayName("Should send email when no notices and call Gemini with empty array")
    void case02() {
        NotificationGatewayImpl gateway = new NotificationGatewayImpl(geminiService, emailService);

        when(geminiService.generateSubject("[]")).thenReturn("Assunto vazio");
        when(geminiService.generateIntroduction("[]")).thenReturn("Introdução vazia");

        gateway.sendNotification("b@example.com", List.of());

        verify(geminiService).generateSubject("[]");
        verify(geminiService).generateIntroduction("[]");
        verify(emailService).sendEmail(eq("b@example.com"), eq("Assunto vazio"), anyString());
    }

}