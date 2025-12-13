package com.lucasmoraist.news_letter_ai.infrastructure.web.dto;

import com.lucasmoraist.news_letter_ai.domain.enums.GenderEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CustomerDTO(
        @NotBlank(message = "Name is mandatory")
        @Size(min = 3, message = "Name must have at least 3 characters")
        String name,
        @NotBlank(message = "Email is mandatory")
        @Email(message = "Email should be valid")
        String email,
        @NotBlank(message = "Phone number is mandatory")
        @Pattern(regexp = "^\\(?\\d{2}\\)?\\s?\\d{4,5}-?\\d{4}$",
                message = "Phone number should be a valid format (e.g., (11) 99999-9999)")
        String phoneNumber,
        @NotNull(message = "Gender is mandatory")
        GenderEnum gender
) {

}
