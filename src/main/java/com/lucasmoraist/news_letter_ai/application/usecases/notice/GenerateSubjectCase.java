package com.lucasmoraist.news_letter_ai.application.usecases.notice;

import com.lucasmoraist.news_letter_ai.application.gateway.GenAIGateway;
import com.lucasmoraist.news_letter_ai.application.utils.PromptUtils;
import org.springframework.cache.annotation.Cacheable;

public class GenerateSubjectCase {

    private final GenAIGateway genAIGateway;

    public GenerateSubjectCase(GenAIGateway genAIGateway) {
        this.genAIGateway = genAIGateway;
    }

    @Cacheable(
            value = "gemini-investment-news-subject",
            key = "'daily-news'"
    )
    public String execute(String notices) {
        final String prompt = PromptUtils.loadPromptTemplate("prompts/subject-prompt.txt");
        final String promptWithNotices = prompt.replace("{{NOTICES}}", notices);

        return genAIGateway.sendPromptToGemini(promptWithNotices);
    }

}
