package com.lucasmoraist.news_letter_ai.infrastructure.database.persistence;

import com.lucasmoraist.news_letter_ai.application.gateway.CustomerPersistence;
import com.lucasmoraist.news_letter_ai.application.mapper.CustomerMapper;
import com.lucasmoraist.news_letter_ai.domain.exceptions.UniqueException;
import com.lucasmoraist.news_letter_ai.domain.model.Customer;
import com.lucasmoraist.news_letter_ai.infrastructure.database.entity.CustomerEntity;
import com.lucasmoraist.news_letter_ai.infrastructure.database.repository.CustomerRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Log4j2
@Component
public class CustomerPersistenceImpl implements CustomerPersistence {

    private final CustomerRepository repository;

    public CustomerPersistenceImpl(CustomerRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public Customer saveCustomer(Customer customer) {
        emailAlreadyExists(customer.email());
        log.info("Saving customer with email [{}]", customer.email());

        CustomerEntity customerEntity = CustomerMapper.toEntity(customer);
        CustomerEntity customerSaved = this.repository.save(customerEntity);

        return CustomerMapper.toDomain(customerSaved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Customer> findAllCustomers() {
        return this.repository.findAll()
                .stream()
                .map(CustomerMapper::toDomain)
                .toList();
    }

    private void emailAlreadyExists(String email) {
        this.repository.findByEmail(email)
                .ifPresent(c -> {
                    log.error("Email [{}] is already registered", c.getEmail());
                    throw new UniqueException("Email already exists: " + c.getEmail());
                });
    }

}
