package com.lucasmoraist.news_letter_ai.infrastructure.web.routes;

import com.lucasmoraist.news_letter_ai.infrastructure.web.dto.CustomerDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/customer")
public interface CustomerRoutes {

    @PostMapping("/save")
    ResponseEntity<Void> saveCustomer(@ModelAttribute("customer") @Valid CustomerDTO dto);
}
