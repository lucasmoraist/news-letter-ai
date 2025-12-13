package com.lucasmoraist.news_letter_ai.application.usecases.notification;

import com.lucasmoraist.news_letter_ai.application.gateway.CustomerPersistence;
import com.lucasmoraist.news_letter_ai.application.gateway.NotificationGateway;
import com.lucasmoraist.news_letter_ai.domain.model.Customer;
import com.lucasmoraist.news_letter_ai.domain.model.Notice;

import java.util.List;

public record SendNotification(CustomerPersistence customerPersistence, NotificationGateway notificationGateway) {

    public void execute(List<Notice> notices) {
        List<Customer> customers = customerPersistence.findAllCustomers();

        customers.forEach(customer ->
                notificationGateway.sendNotification(customer.email(), notices)
        );
    }

}
