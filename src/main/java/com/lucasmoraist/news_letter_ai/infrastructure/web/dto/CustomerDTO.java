package com.lucasmoraist.news_letter_ai.infrastructure.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CustomerDTO(
        @NotBlank(message = "Name is mandatory")
        @Size(min = 3, message = "Name must have at least 3 characters")
        String name,
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "Phone number is mandatory")
        String phoneNumber,
        @NotBlank(message = "Gender is mandatory")
        String gender
) {

}
