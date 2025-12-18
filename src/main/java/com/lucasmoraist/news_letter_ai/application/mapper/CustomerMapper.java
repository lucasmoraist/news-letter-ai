package com.lucasmoraist.news_letter_ai.application.mapper;

import com.lucasmoraist.news_letter_ai.domain.exceptions.ContentException;
import com.lucasmoraist.news_letter_ai.domain.model.Customer;
import com.lucasmoraist.news_letter_ai.domain.model.Theme;
import com.lucasmoraist.news_letter_ai.infrastructure.database.entity.CustomerEntity;
import com.lucasmoraist.news_letter_ai.infrastructure.database.entity.ThemeEntity;
import com.lucasmoraist.news_letter_ai.infrastructure.web.dto.CustomerDTO;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import java.util.regex.Pattern;

public final class CustomerMapper {

    private CustomerMapper() {
        throw new IllegalStateException("Utility class");
    }

    private static final Pattern VALID_NAME_PATTERN =
            Pattern.compile("^[\\p{L} .'-]+$", Pattern.CASE_INSENSITIVE);

    public static Customer toDomain(CustomerEntity customerEntity) {
        return new Customer(
                customerEntity.getId(),
                validateName(customerEntity.getName()),
                customerEntity.getEmail(),
                customerEntity.getPhoneNumber(),
                customerEntity.getGender(),
                customerEntity.getIsActive(),
                customerEntity.getThemes()
                        .stream()
                        .map(c -> new Theme(
                                c.getId(),
                                c.getName(),
                                null
                        ))
                        .toList()
        );
    }

    public static Customer toDomain(CustomerDTO customerDTO) {
        return new Customer(
                null,
                validateName(customerDTO.name()),
                customerDTO.email(),
                customerDTO.phoneNumber(),
                customerDTO.gender(),
                false,
                customerDTO.desiredThemes()
                        .stream()
                        .map(c -> new Theme(
                                null,
                                c.name(),
                                null
                        ))
                        .toList()
        );
    }

    public static CustomerEntity toEntity(Customer customer) {
        return new CustomerEntity(
                customer.id(),
                validateName(customer.name()),
                customer.email(),
                customer.phoneNumber(),
                customer.gender(),
                customer.isActive(),
                customer.themes()
                        .stream()
                        .map(c -> new ThemeEntity(
                                c.id(),
                                c.name(),
                                null
                        ))
                        .toList()
        );
    }

    private static String validateName(String name) {
        String sanitizedName = Jsoup.clean(name, Safelist.none());

        if (!VALID_NAME_PATTERN.matcher(sanitizedName).matches()) {
            throw new ContentException("Invalid name format");
        }

        return sanitizedName;
    }

}
