package com.lucasmoraist.news_letter_ai.infrastructure.messages.email;

import com.lucasmoraist.news_letter_ai.domain.exceptions.EmailException;
import com.lucasmoraist.news_letter_ai.infrastructure.client.BrevoFeignClient;
import com.lucasmoraist.news_letter_ai.infrastructure.messages.dto.BrevoDtos;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

    @Mock
    BrevoFeignClient brevoFeignClient;
    @InjectMocks
    EmailService emailService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(emailService, "senderEmail", "no-reply@example.com");
    }

    @Test
    @DisplayName("Should call BrevoFeignClient with proper request")
    void case01() {
        String userEmail = "user@example.com";
        String subject = "Teste assunto";
        String body = "<p>conteúdo</p>";

        when(brevoFeignClient.sendTransactionalEmail(any(BrevoDtos.SendSmtpEmailRequest.class)))
                .thenReturn(null);

        emailService.sendEmail(userEmail, subject, body);

        ArgumentCaptor<BrevoDtos.SendSmtpEmailRequest> captor =
                ArgumentCaptor.forClass(BrevoDtos.SendSmtpEmailRequest.class);
        verify(brevoFeignClient, times(1)).sendTransactionalEmail(captor.capture());

        BrevoDtos.SendSmtpEmailRequest request = captor.getValue();

        assertEquals(subject, ReflectionTestUtils.getField(request, "subject"));
        assertEquals(body, ReflectionTestUtils.getField(request, "htmlContent"));

        Object sender = ReflectionTestUtils.getField(request, "sender");
        assertEquals("no-reply@example.com", ReflectionTestUtils.getField(sender, "email"));

        Object toList = ReflectionTestUtils.getField(request, "to");
        assertNotNull(toList);
        List<?> recipients = (List<?>) toList;
        assertEquals(1, recipients.size());
        Object recipient = recipients.get(0);
        assertEquals(userEmail, ReflectionTestUtils.getField(recipient, "email"));
    }

    @Test
    @DisplayName("Should wrap FeignException into EmailException")
    void case02() {
        FeignException feignEx = mock(FeignException.class);
        when(feignEx.status()).thenReturn(500);
        when(feignEx.contentUTF8()).thenReturn("error");
        doThrow(feignEx).when(brevoFeignClient).sendTransactionalEmail(any(BrevoDtos.SendSmtpEmailRequest.class));

        EmailException thrown = assertThrows(EmailException.class,
                () -> emailService.sendEmail("u@example.com", "s", "b"));

        assertSame(feignEx, thrown.getCause());
    }

    @Test
    @DisplayName("Should wrap generic Exception into EmailException")
    void case03() {

        RuntimeException runtimeEx = new RuntimeException("boom");
        doThrow(runtimeEx).when(brevoFeignClient).sendTransactionalEmail(any(BrevoDtos.SendSmtpEmailRequest.class));

        EmailException thrown = assertThrows(EmailException.class,
                () -> emailService.sendEmail("u2@example.com", "s2", "b2"));

        assertSame(runtimeEx, thrown.getCause());
    }

}