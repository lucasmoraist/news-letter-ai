package com.lucasmoraist.news_letter_ai.infrastructure.web.routes;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public interface CustomerViewRoutes {

    @GetMapping({"/", "/register"})
    String showRegistrationForm(Model model);

}
