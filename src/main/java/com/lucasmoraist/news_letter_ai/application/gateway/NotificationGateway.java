package com.lucasmoraist.news_letter_ai.application.gateway;

import com.lucasmoraist.news_letter_ai.domain.model.Notice;

public interface NotificationGateway {
    void sendNotification(String email, Notice notice);
}
