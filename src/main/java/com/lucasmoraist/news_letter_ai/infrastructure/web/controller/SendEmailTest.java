package com.lucasmoraist.news_letter_ai.infrastructure.web.controller;

import com.lucasmoraist.news_letter_ai.domain.model.Notice;
import com.lucasmoraist.news_letter_ai.infrastructure.messages.email.EmailService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test-email")
public class SendEmailTest {

    private final EmailService emailService;

    public SendEmailTest(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping
    public ResponseEntity<Void> sendTestEmail(@RequestHeader String email) {
        List<Notice> notices = List.of(new Notice(
                "Teste de notícia",
                "Esta é uma notícia de teste para verificar o envio de e-mails.",
                "https://example.com/test-news",
                "2024-06-10",
                "Fonte de Teste"
        ));
        this.emailService.sendNotice(email, "Teste", notices);
        return ResponseEntity.ok().build();
    }

}
