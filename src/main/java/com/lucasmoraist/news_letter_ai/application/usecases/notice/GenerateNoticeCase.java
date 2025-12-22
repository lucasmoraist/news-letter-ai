package com.lucasmoraist.news_letter_ai.application.usecases.notice;

import com.lucasmoraist.news_letter_ai.application.gateway.GenAIGateway;
import com.lucasmoraist.news_letter_ai.application.utils.PromptUtils;
import com.lucasmoraist.news_letter_ai.domain.enums.ThemeEnum;
import org.springframework.cache.annotation.Cacheable;

import java.time.LocalDate;

public class GenerateNoticeCase {

    private final GenAIGateway genAIGateway;

    public GenerateNoticeCase(GenAIGateway genAIGateway) {
        this.genAIGateway = genAIGateway;
    }

    @Cacheable(
            value = "gemini-investment-news",
            key = "'daily-news'"
    )
    public String execute() {
        final String loadPrompt = PromptUtils.loadPromptTemplate("prompts/notice-prompt.txt");
        final LocalDate yesterday = LocalDate.now().minusDays(1);
        final String prompt = loadPrompt
                .replace("{{DATE}}", yesterday.toString())
                .replace("{{THEME}}", ThemeEnum.FINANCE.getDescription());

        return genAIGateway.sendPromptToGemini(prompt);
    }

}
