package com.lucasmoraist.news_letter_ai.infrastructure.messages.email;

import com.lucasmoraist.news_letter_ai.domain.exceptions.EmailException;
import com.lucasmoraist.news_letter_ai.infrastructure.client.BrevoFeignClient;
import com.lucasmoraist.news_letter_ai.infrastructure.messages.dto.BrevoDtos;
import feign.FeignException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Log4j2
@Service
public class EmailService {

    private final BrevoFeignClient brevoFeignClient;

    @Value("${secrets.brevo.sender-email}")
    private String senderEmail;

    public EmailService(BrevoFeignClient brevoFeignClient) {
        this.brevoFeignClient = brevoFeignClient;
    }

    public void sendEmail(String userEmail, String subject, String emailBody) {
        try {
            BrevoDtos.SendSmtpEmailRequest requestBody = BrevoDtos.SendSmtpEmailRequest.builder()
                    .sender(BrevoDtos.Sender.builder().email(senderEmail).build())
                    .to(Collections.singletonList(BrevoDtos.Recipient.builder().email(userEmail).build()))
                    .subject(subject)
                    .htmlContent(emailBody)
                    .build();

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
