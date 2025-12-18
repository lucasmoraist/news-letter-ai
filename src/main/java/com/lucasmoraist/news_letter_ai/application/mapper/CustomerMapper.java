package com.lucasmoraist.news_letter_ai.application.mapper;

import com.lucasmoraist.news_letter_ai.domain.exceptions.ContentException;
import com.lucasmoraist.news_letter_ai.domain.model.Customer;
import com.lucasmoraist.news_letter_ai.domain.model.Theme;
import com.lucasmoraist.news_letter_ai.infrastructure.database.entity.CustomerEntity;
import com.lucasmoraist.news_letter_ai.infrastructure.database.entity.ThemeEntity;
import com.lucasmoraist.news_letter_ai.infrastructure.web.dto.CustomerDTO;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import java.util.List;
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
                        .map(customer -> new Theme(
                                customer.getId(),
                                customer.getName(),
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
                        .map(customer -> new Theme(
                                null,
                                customer.name(),
                                null
                        ))
                        .toList()
        );
    }

    public static CustomerEntity toEntity(Customer customer) {
        CustomerEntity entity = new CustomerEntity();
        entity.setId(customer.id());
        entity.setName(customer.name());
        entity.setEmail(customer.email());
        entity.setPhoneNumber(customer.phoneNumber());
        entity.setGender(customer.gender());
        entity.setIsActive(customer.isActive());
        if (customer.themes() != null) {
            List<ThemeEntity> themes = customer.themes().stream().map(t -> {
                ThemeEntity te = new ThemeEntity();
                te.setId(t.id());
                te.setName(t.name());
                te.setCustomer(entity);
                return te;
            }).toList();
            entity.setThemes(themes);
        }
        return entity;
    }

    private static String validateName(String name) {
        String sanitizedName = Jsoup.clean(name, Safelist.none());

        if (!VALID_NAME_PATTERN.matcher(sanitizedName).matches()) {
            throw new ContentException("Invalid name format");
        }

        return sanitizedName;
    }

}
