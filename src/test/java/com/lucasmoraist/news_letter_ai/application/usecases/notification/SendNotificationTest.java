package com.lucasmoraist.news_letter_ai.application.usecases.notification;

import com.lucasmoraist.news_letter_ai.application.gateway.CustomerPersistence;
import com.lucasmoraist.news_letter_ai.application.gateway.NotificationGateway;
import com.lucasmoraist.news_letter_ai.domain.model.Customer;
import com.lucasmoraist.news_letter_ai.domain.model.Notice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SendNotificationTest {

    @Mock
    CustomerPersistence customerPersistence;
    @Mock
    NotificationGateway notificationGateway;
    @InjectMocks
    SendNotification sendNotification;

    @Test
    @DisplayName("Should send notification to all customers")
    void case01() {
        Customer customer1 = mock(Customer.class);
        Customer customer2 = mock(Customer.class);
        when(customer1.email()).thenReturn("a@example.com");
        when(customer2.email()).thenReturn("b@example.com");

        when(customerPersistence.findAllCustomers()).thenReturn(List.of(customer1, customer2));

        Notice notice = mock(Notice.class);
        List<Notice> notices = List.of(notice);

        sendNotification.execute(notices);

        verify(notificationGateway).sendNotification("a@example.com", notices);
        verify(notificationGateway).sendNotification("b@example.com", notices);
        verifyNoMoreInteractions(notificationGateway);
    }

    @Test
    @DisplayName("Should not call gateway when no customers")
    void case02() {
        when(customerPersistence.findAllCustomers()).thenReturn(List.of());

        sendNotification.execute(List.of());

        verifyNoInteractions(notificationGateway);
    }

}