package com.lucasmoraist.news_letter_ai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableCaching
@EnableScheduling
@SpringBootApplication
public class NewsLetterAiApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsLetterAiApplication.class, args);
	}

}
