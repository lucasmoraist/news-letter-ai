package com.lucasmoraist.news_letter_ai.application.gateway;

import com.lucasmoraist.news_letter_ai.domain.model.Notice;

import java.util.List;

public interface NotificationGateway {
    void sendNotification(String email, List<Notice> notices);
}
