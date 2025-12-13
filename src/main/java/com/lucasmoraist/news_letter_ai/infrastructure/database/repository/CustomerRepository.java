package com.lucasmoraist.news_letter_ai.infrastructure.database.repository;

import com.lucasmoraist.news_letter_ai.infrastructure.database.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<CustomerEntity, UUID> {

}
