package com.lucasmoraist.news_letter_ai.infrastructure.config.usecases;

import com.lucasmoraist.news_letter_ai.application.gateway.CustomerPersistence;
import com.lucasmoraist.news_letter_ai.application.usecases.customer.CreateCustomerCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomerUseCase {

    @Bean
    public CreateCustomerCase createCustomerCase(CustomerPersistence customerPersistence) {
        return new CreateCustomerCase(customerPersistence);
    }

}
