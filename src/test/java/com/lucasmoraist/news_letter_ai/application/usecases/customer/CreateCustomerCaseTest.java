package com.lucasmoraist.news_letter_ai.application.usecases.customer;

import com.lucasmoraist.news_letter_ai.application.gateway.CustomerPersistence;
import com.lucasmoraist.news_letter_ai.application.mapper.CustomerMapper;
import com.lucasmoraist.news_letter_ai.domain.model.Customer;
import com.lucasmoraist.news_letter_ai.infrastructure.web.dto.CustomerDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CreateCustomerCaseTest {

    @Mock
    CustomerPersistence customerPersistence;
    @InjectMocks
    CreateCustomerCase createCustomerCase;

    @Test
    @DisplayName("Should create a customer successfully")
    void case01() {
        CustomerDTO dto = new CustomerDTO(
                "John Doe",
                "johndoe@email.com",
                "1234567890",
                "MALE"
        );
        Customer customer = CustomerMapper.toDomain(dto);

        createCustomerCase.execute(customer);

        verify(customerPersistence, times(1)).saveCustomer(customer);
    }

}