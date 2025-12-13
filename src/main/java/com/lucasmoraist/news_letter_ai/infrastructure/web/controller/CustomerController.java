package com.lucasmoraist.news_letter_ai.infrastructure.web.controller;

import com.lucasmoraist.news_letter_ai.application.mapper.CustomerMapper;
import com.lucasmoraist.news_letter_ai.application.usecases.customer.CreateCustomerCase;
import com.lucasmoraist.news_letter_ai.domain.model.Customer;
import com.lucasmoraist.news_letter_ai.infrastructure.web.dto.CustomerDTO;
import com.lucasmoraist.news_letter_ai.infrastructure.web.routes.CustomerRoutes;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomerRoutes {

    private final CreateCustomerCase createCustomerCase;

    @Override
    public ResponseEntity<Void> saveCustomer(CustomerDTO customerDTO) {
        log.debug("Received request to save customer");
        Customer customer = CustomerMapper.toDomain(customerDTO);
        this.createCustomerCase.execute(customer);
        return ResponseEntity.status(201).build();
    }

}
