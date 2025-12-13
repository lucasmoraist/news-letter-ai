package com.lucasmoraist.news_letter_ai.application.usecases.customer;

import com.lucasmoraist.news_letter_ai.application.gateway.CustomerPersistence;
import com.lucasmoraist.news_letter_ai.domain.model.Customer;

public record CreateCustomerCase(CustomerPersistence customerPersistence) {

    public void execute(Customer customer) {
        this.customerPersistence.saveCustomer(customer);
        // TODO: Deve enviar notificação de boas vindas
        // TODO: Deve pegar as ultimas noticias e enviar para o cliente novo
    }

}
