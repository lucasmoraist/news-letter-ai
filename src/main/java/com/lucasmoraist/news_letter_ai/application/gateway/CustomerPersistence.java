package com.lucasmoraist.news_letter_ai.application.gateway;

import com.lucasmoraist.news_letter_ai.domain.model.Customer;

import java.util.List;

public interface CustomerPersistence {
    Customer saveCustomer(Customer customer);
    List<Customer> findAllCustomers();
}
