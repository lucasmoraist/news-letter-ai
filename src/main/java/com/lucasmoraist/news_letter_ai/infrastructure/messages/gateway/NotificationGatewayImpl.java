package com.lucasmoraist.news_letter_ai.infrastructure.messages.gateway;

import com.lucasmoraist.news_letter_ai.application.gateway.NotificationGateway;
import com.lucasmoraist.news_letter_ai.application.usecases.notice.GenerateIntroductionCase;
import com.lucasmoraist.news_letter_ai.application.usecases.notice.GenerateSubjectCase;
import com.lucasmoraist.news_letter_ai.domain.model.Notice;
import com.lucasmoraist.news_letter_ai.infrastructure.messages.email.EmailService;
import com.lucasmoraist.news_letter_ai.infrastructure.messages.utils.BodyTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class NotificationGatewayImpl implements NotificationGateway {

    private final GenerateSubjectCase generateSubjectCase;
    private final GenerateIntroductionCase generateIntroductionCase;
    private final EmailService emailService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void sendNotification(String email, List<Notice> notices) {
        String subject = buildSubject(notices);
        String introduction = buildIntroduction(notices);

        String emailBody = BodyTemplate.buildEmail(notices, introduction);
        log.debug("Email body built: {}", emailBody);

        this.emailService.sendEmail(email, subject, emailBody);
    }

    private String buildSubject(List<Notice> notices) {
        log.debug("Building subject for notices: {}", notices);
        String noticesAsString = objectMapper.writeValueAsString(notices);
        return generateSubjectCase.execute(noticesAsString);
    }

    private String buildIntroduction(List<Notice> notices) {
        log.debug("Building introduction for notices: {}", notices);
        String noticesAsString = objectMapper.writeValueAsString(notices);
        return generateIntroductionCase.execute(noticesAsString);
    }

}
