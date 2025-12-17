package com.lucasmoraist.news_letter_ai.infrastructure.web.controller;

import com.lucasmoraist.news_letter_ai.application.usecases.customer.CreateCustomerCase;
import com.lucasmoraist.news_letter_ai.domain.enums.GenderEnum;
import com.lucasmoraist.news_letter_ai.infrastructure.web.dto.CustomerDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    CreateCustomerCase createCustomerCase;

    @Test
    @DisplayName("Should save a customer and return 201 status")
    void case01() throws Exception {
        CustomerDTO dto = new CustomerDTO(
                "John Doe",
                "johndoe@email.com",
                "1234567890",
                GenderEnum.MALE
        );

        mockMvc.perform(post("/api/v1/customer/save")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());


        verify(createCustomerCase, times(1))
                .execute(any());
    }

}