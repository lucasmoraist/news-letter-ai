package com.lucasmoraist.news_letter_ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@EnableFeignClients
@SpringBootApplication
public class NewsLetterAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsLetterAiApplication.class, args);
	}

}
