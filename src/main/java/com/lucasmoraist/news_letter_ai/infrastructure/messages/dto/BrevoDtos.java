package com.lucasmoraist.news_letter_ai.infrastructure.messages.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

public class BrevoDtos {

    @Data
    @Builder
    public static class Sender {
        private String email;
    }

    @Data
    @Builder
    public static class Recipient {
        private String email;
    }

    @Data
    @Builder
    public static class SendSmtpEmailRequest {
        private Sender sender;
        private List<Recipient> to;
        private String subject;
        private String htmlContent;
    }

}
