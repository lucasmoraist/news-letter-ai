package com.lucasmoraist.news_letter_ai.infrastructure.messages.email;

import com.lucasmoraist.news_letter_ai.domain.exceptions.EmailException;
import com.lucasmoraist.news_letter_ai.domain.model.Notice;
import com.lucasmoraist.news_letter_ai.infrastructure.client.BrevoFeignClient;
import com.lucasmoraist.news_letter_ai.infrastructure.genai.GeminiService;
import com.lucasmoraist.news_letter_ai.infrastructure.messages.dto.BrevoDtos;
import com.lucasmoraist.news_letter_ai.infrastructure.messages.utils.BodyTemplate;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.Collections;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailService {

    private final BrevoFeignClient brevoFeignClient;
    private final GeminiService geminiService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.mail.username}")
    private String senderEmail;

    public void sendNotice(String userEmail, String subject, List<Notice> notices) {
        String noticesAsString = objectMapper.writeValueAsString(notices);
        String introduction = geminiService.generateIntroduction(noticesAsString);

        String emailBody = BodyTemplate.buildEmail(notices, introduction);

        this.sendEmail(userEmail, subject, emailBody);
    }

    private void sendEmail(String userEmail, String subject, String emailBody) {
        BrevoDtos.SendSmtpEmailRequest requestBody = BrevoDtos.SendSmtpEmailRequest.builder()
                .sender(BrevoDtos.Sender.builder().email(senderEmail).build())
                .to(Collections.singletonList(BrevoDtos.Recipient.builder().email(userEmail).build()))
                .subject(subject)
                .htmlContent(emailBody)
                .build();

        try {
            brevoFeignClient.sendTransactionalEmail(requestBody);

            log.info("Email sent successfully to {}", userEmail);
        } catch (FeignException e) {
            log.error("Failed to send email to {} via Brevo API: Status={}, Message={}",
                    userEmail, e.status(), e.contentUTF8());
            throw new EmailException("Failed to send email to " + userEmail, e);
        } catch (Exception e) {
            log.error("An unexpected error occurred while sending email to {}: {}", userEmail, e.getMessage());
            throw new EmailException("Failed to send email to " + userEmail, e);
        }
    }

}
