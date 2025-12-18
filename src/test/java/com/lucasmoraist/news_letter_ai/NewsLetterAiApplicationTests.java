package com.lucasmoraist.news_letter_ai;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
class NewsLetterAiApplicationTests {

	@Test
	void contextLoads() {
        assertDoesNotThrow(() -> NewsLetterAiApplication.main(new String[] {"--spring.profiles.active=test"}));
	}

}
