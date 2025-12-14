package com.lucasmoraist.news_letter_ai.infrastructure.web.routes;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/notices")
public interface NoticesRoutes {

    @PostMapping("/sent")
    ResponseEntity<Void> sendNotices();

}
