package com.lucasmoraist.news_letter_ai.infrastructure.web.controller;

import com.lucasmoraist.news_letter_ai.domain.enums.GenderEnum;
import com.lucasmoraist.news_letter_ai.domain.enums.ThemeEnum;
import com.lucasmoraist.news_letter_ai.infrastructure.web.dto.CustomerDTO;
import com.lucasmoraist.news_letter_ai.infrastructure.web.routes.CustomerViewRoutes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.ArrayList;

@Controller
public class CustomerViewController implements CustomerViewRoutes {

    @Override
    public String showRegistrationForm(Model model) {
        model.addAttribute("customer", new CustomerDTO("", "", "", null, new ArrayList<>()));

        model.addAttribute("genders", GenderEnum.values());
        model.addAttribute("themes", ThemeEnum.values());

        return "registration";
    }

}
