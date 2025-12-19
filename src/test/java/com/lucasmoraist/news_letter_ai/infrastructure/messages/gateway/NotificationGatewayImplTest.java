package com.lucasmoraist.news_letter_ai.infrastructure.messages.gateway;

import com.lucasmoraist.news_letter_ai.application.usecases.notice.GenerateIntroductionCase;
import com.lucasmoraist.news_letter_ai.application.usecases.notice.GenerateSubjectCase;
import com.lucasmoraist.news_letter_ai.domain.model.Notice;
import com.lucasmoraist.news_letter_ai.infrastructure.messages.email.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationGatewayImplTest {

    @Mock
    GenerateSubjectCase generateSubjectCase;
    @Mock
    GenerateIntroductionCase generateIntroductionCase;
    @Mock
    EmailService emailService;
    @InjectMocks
    NotificationGatewayImpl notificationGateway;

    @Test
    @DisplayName("Should build subject and introduction and send email")
    void case01() {
        Notice notice = mock(Notice.class);
        List<Notice> notices = List.of(notice);

        when(generateSubjectCase.execute(anyString())).thenReturn("Assunto gerado");
        when(generateIntroductionCase.execute(anyString())).thenReturn("Introdução gerada");

        notificationGateway.sendNotification("a@example.com", notices);

        verify(generateSubjectCase, times(1)).execute(anyString());
        verify(generateIntroductionCase, times(1)).execute(anyString());

        ArgumentCaptor<String> bodyCaptor = ArgumentCaptor.forClass(String.class);
        verify(emailService).sendEmail(eq("a@example.com"), eq("Assunto gerado"), bodyCaptor.capture());
        assertTrue(bodyCaptor.getValue().contains("Introdução gerada"));
    }

    @Test
    @DisplayName("Should send email when no notices and call Gemini with empty array")
    void case02() {
        when(generateSubjectCase.execute("[]")).thenReturn("Assunto vazio");
        when(generateIntroductionCase.execute("[]")).thenReturn("Introdução vazia");

        notificationGateway.sendNotification("b@example.com", List.of());

        verify(generateSubjectCase, times(1)).execute("[]");
        verify(generateIntroductionCase, times(1)).execute("[]");
        verify(emailService).sendEmail(eq("b@example.com"), eq("Assunto vazio"), anyString());
    }

}