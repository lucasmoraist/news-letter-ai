package com.lucasmoraist.news_letter_ai.factory;

import com.lucasmoraist.news_letter_ai.domain.enums.GenderEnum;
import com.lucasmoraist.news_letter_ai.domain.enums.ThemeEnum;
import com.lucasmoraist.news_letter_ai.infrastructure.web.dto.CustomerDTO;

import java.util.List;

public final class CustomerFactory {

    public static CustomerDTO createDTO() {
        return new CustomerDTO(
                "John Doe",
                "johndoe@email.com",
                "11940028922",
                GenderEnum.MALE,
                List.of(ThemeEnum.TECHNOLOGY)
        );
    }

}
