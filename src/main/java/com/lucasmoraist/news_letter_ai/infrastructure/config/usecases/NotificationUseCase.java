package com.lucasmoraist.news_letter_ai.infrastructure.config.usecases;

import com.lucasmoraist.news_letter_ai.application.gateway.CustomerPersistence;
import com.lucasmoraist.news_letter_ai.application.gateway.NotificationGateway;
import com.lucasmoraist.news_letter_ai.application.usecases.notification.SendNotification;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationUseCase {

    @Bean
    public SendNotification sendNotification(CustomerPersistence customerPersistence, NotificationGateway notificationGateway) {
        return new SendNotification(customerPersistence, notificationGateway);
    }

}
