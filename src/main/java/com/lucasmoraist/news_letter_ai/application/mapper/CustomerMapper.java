package com.lucasmoraist.news_letter_ai.application.mapper;

import com.lucasmoraist.news_letter_ai.domain.model.Customer;
import com.lucasmoraist.news_letter_ai.infrastructure.database.entity.CustomerEntity;
import com.lucasmoraist.news_letter_ai.infrastructure.web.dto.CustomerDTO;

public final class CustomerMapper {

    private CustomerMapper() {
        throw new IllegalStateException("Utility class");
    }

    public static Customer toDomain(CustomerEntity customerEntity) {
        return new Customer(
                customerEntity.getId(),
                customerEntity.getName(),
                customerEntity.getEmail(),
                customerEntity.getPhoneNumber(),
                customerEntity.getGender(),
                customerEntity.getIsActive()
        );
    }

    public static Customer toDomain(CustomerDTO customerDTO) {
        return new Customer(
                null,
                customerDTO.name(),
                customerDTO.email(),
                customerDTO.phoneNumber(),
                customerDTO.gender(),
                false
        );
    }

    public static CustomerEntity toEntity(Customer customer) {
        return new CustomerEntity(
                customer.id(),
                customer.name(),
                customer.email(),
                customer.phoneNumber(),
                customer.gender(),
                customer.isActive()
        );
    }

}
