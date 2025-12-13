package com.lucasmoraist.news_letter_ai.infrastructure.web.routes;

import com.lucasmoraist.news_letter_ai.infrastructure.web.dto.CustomerDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/customer")
public interface CustomerRoutes {

    @PostMapping("/save")
    ResponseEntity<Void> saveCustomer(@RequestBody CustomerDTO customerDTO);
}
