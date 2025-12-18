package com.lucasmoraist.news_letter_ai.infrastructure.database.persistence;

import com.lucasmoraist.news_letter_ai.application.gateway.CustomerPersistence;
import com.lucasmoraist.news_letter_ai.application.mapper.CustomerMapper;
import com.lucasmoraist.news_letter_ai.domain.exceptions.UniqueException;
import com.lucasmoraist.news_letter_ai.domain.model.Customer;
import com.lucasmoraist.news_letter_ai.infrastructure.database.entity.CustomerEntity;
import com.lucasmoraist.news_letter_ai.infrastructure.database.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

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
        try {
            log.info("Saving customer with email [{}]", customer.email());
            CustomerEntity customerEntity = CustomerMapper.toEntity(customer);
            return CustomerMapper.toDomain(this.repository.save(customerEntity));
        } catch (ConstraintViolationException | DataIntegrityViolationException ex) {
            log.error("Email [{}] is already registered", customer.email());
            throw new UniqueException("Email already registered", ex);
        }
    }

    @Override
    public List<Customer> findAllCustomers() {
        return this.repository.findAll()
                .stream()
                .map(CustomerMapper::toDomain)
                .toList();
    }

}
