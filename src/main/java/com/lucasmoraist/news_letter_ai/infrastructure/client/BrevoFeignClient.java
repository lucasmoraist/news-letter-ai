package com.lucasmoraist.news_letter_ai.infrastructure.client;

import com.lucasmoraist.news_letter_ai.infrastructure.config.BrevoFeignConfig;
import com.lucasmoraist.news_letter_ai.infrastructure.messages.dto.BrevoDtos;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(
        name = "brevoClient",
        url = "${secrets.brevo.api-url}", // Definiremos esta propriedade no application.yml
        configuration = BrevoFeignConfig.class
)
public interface BrevoFeignClient {

    @PostMapping("/smtp/email")
    Response sendTransactionalEmail(@RequestBody BrevoDtos.SendSmtpEmailRequest requestBody);

}
