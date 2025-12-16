package com.lucasmoraist.news_letter_ai.infrastructure.messages.email;

import com.lucasmoraist.news_letter_ai.domain.exceptions.EmailException;
import com.lucasmoraist.news_letter_ai.domain.model.Notice;
import com.lucasmoraist.news_letter_ai.infrastructure.genai.GeminiService;
import com.lucasmoraist.news_letter_ai.infrastructure.messages.utils.BodyTemplate;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final GeminiService geminiService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${spring.mail.username}") // Adicione esta linha
    private String senderEmail;

    public void sendNotice(String userEmail, String subject, List<Notice> notices) {
        String noticesAsString = objectMapper.writeValueAsString(notices);
        String introduction = geminiService.generateIntroduction(noticesAsString);

        String emailBody = BodyTemplate.buildEmail(notices, introduction);

        this.sendEmail(userEmail, subject, emailBody);
    }

    private void sendEmail(String userEmail, String subject, String emailBody) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(senderEmail);
            helper.setTo(userEmail);
            helper.setSubject(subject);
            helper.setText(emailBody, true);

            javaMailSender.send(message);
            log.info("Email sent successfully to {}", userEmail);
        } catch (MessagingException e) {
            log.error("Failed to send email to {}: {}", userEmail, e.getMessage());
            throw new EmailException("Failed to send email to " + userEmail, e);
        }
    }

}
