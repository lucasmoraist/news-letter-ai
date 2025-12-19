package com.lucasmoraist.news_letter_ai.infrastructure.config.usecases;

import com.lucasmoraist.news_letter_ai.application.gateway.GenAIGateway;
import com.lucasmoraist.news_letter_ai.application.usecases.notice.GenerateIntroductionCase;
import com.lucasmoraist.news_letter_ai.application.usecases.notice.GenerateNoticeCase;
import com.lucasmoraist.news_letter_ai.application.usecases.notice.GenerateSubjectCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NoticeUseCase {

    @Bean
    public GenerateNoticeCase generateNoticeCase(GenAIGateway genAIGateway) {
        return new GenerateNoticeCase(genAIGateway);
    }

    @Bean
    public GenerateSubjectCase generateSubjectCase(GenAIGateway genAIGateway) {
        return new GenerateSubjectCase(genAIGateway);
    }

    @Bean
    public GenerateIntroductionCase generateIntroductionCase(GenAIGateway genAIGateway) {
        return new GenerateIntroductionCase(genAIGateway);
    }

}
