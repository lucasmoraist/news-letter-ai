//package com.lucasmoraist.news_letter_ai.infrastructure.web.controller;
//
//import com.lucasmoraist.news_letter_ai.application.usecases.customer.CreateCustomerCase;
//import com.lucasmoraist.news_letter_ai.factory.CustomerFactory;
//import com.lucasmoraist.news_letter_ai.infrastructure.web.dto.CustomerDTO;
//import com.lucasmoraist.news_letter_ai.infrastructure.web.filter.RateLimiterService;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
//import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
//import org.springframework.cache.CacheManager;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.bean.override.mockito.MockitoBean;
//import org.springframework.test.web.servlet.MockMvc;
//import tools.jackson.databind.ObjectMapper;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.times;
//import static org.mockito.Mockito.verify;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@ActiveProfiles("test")
//@WebMvcTest(CustomerController.class)
//@AutoConfigureMockMvc(addFilters = false)
//class CustomerControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//    @Autowired
//    ObjectMapper objectMapper;
//    @MockitoBean
//    CreateCustomerCase createCustomerCase;
//    @MockitoBean
//    RateLimiterService rateLimiterService;
//    @MockitoBean
//    CacheManager cacheManager;
//
//    @Test
//    @DisplayName("Should save a customer and return 201 status")
//    void case01() throws Exception {
//        CustomerDTO dto = CustomerFactory.createDTO();
//
//        mockMvc.perform(post("/api/v1/customer/save")
//                        .contentType("application/json")
//                        .content(objectMapper.writeValueAsString(dto)))
//                .andExpect(status().isCreated());
//
//        verify(createCustomerCase, times(1))
//                .execute(any());
//        verify(rateLimiterService, times(0))
//                .tryAcquire(any());
//        verify(cacheManager, times(0))
//                .getCache(any());
//    }
//
//}