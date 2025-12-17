//package com.lucasmoraist.news_letter_ai.infrastructure.database.persistence;
//
//import com.lucasmoraist.news_letter_ai.application.gateway.CustomerPersistence;
//import com.lucasmoraist.news_letter_ai.application.mapper.CustomerMapper;
//import com.lucasmoraist.news_letter_ai.domain.enums.GenderEnum;
//import com.lucasmoraist.news_letter_ai.domain.exceptions.UniqueException;
//import com.lucasmoraist.news_letter_ai.domain.model.Customer;
//import com.lucasmoraist.news_letter_ai.infrastructure.database.repository.CustomerRepository;
//import com.lucasmoraist.news_letter_ai.infrastructure.web.dto.CustomerDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class CustomerPersistenceImplTest {
//
//    @Autowired
//    CustomerRepository repository;
//    @Autowired
//    CustomerPersistence persistence;
//
//    @BeforeEach
//    void setup() {
//        repository.deleteAll();
//    }
//
//    @Test
//    @DisplayName("Should save a customer successfully")
//    void case01() {
//        CustomerDTO dto = new CustomerDTO(
//                "John Doe",
//                "johndoe@email.com",
//                "1234567890",
//                GenderEnum.MALE
//        );
//        Customer customer = CustomerMapper.toDomain(dto);
//
//        Customer response = persistence.saveCustomer(customer);
//
//        assertNotNull(response);
//        assertEquals(dto.name(), response.name());
//        assertEquals(dto.email(), response.email());
//        assertEquals(dto.phoneNumber(), response.phoneNumber());
//        assertEquals(dto.gender(), response.gender());
//        assertFalse(customer.isActive());
//    }
//
//    @Test
//    @DisplayName("Should find all customers successfully")
//    void case02() {
//        CustomerDTO dto = new CustomerDTO(
//                "John Doe",
//                "johndoe@email.com",
//                "1234567890",
//                GenderEnum.MALE
//        );
//        Customer customer = CustomerMapper.toDomain(dto);
//        persistence.saveCustomer(customer);
//
//        List<Customer> customers = persistence.findAllCustomers();
//
//        assertFalse(customers.isEmpty());
//    }
//
//    @Test
//    @DisplayName("Should throw exception when saving a customer with duplicate email")
//    void case03() {
//        CustomerDTO dto = new CustomerDTO(
//                "John Doe",
//                "johndoe@email.com",
//                "1234567890",
//                GenderEnum.MALE
//        );
//        Customer customer = CustomerMapper.toDomain(dto);
//        persistence.saveCustomer(customer);
//
//        assertThrows(UniqueException.class, () -> persistence.saveCustomer(customer));
//    }
//
//}