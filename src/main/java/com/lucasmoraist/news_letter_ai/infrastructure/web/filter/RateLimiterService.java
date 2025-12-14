package com.lucasmoraist.news_letter_ai.infrastructure.web.filter;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;

@Service
public class RateLimiterService {

    private static final int MAX_REQUESTS = 5;
    private static final Duration WINDOW_TIME = Duration.ofSeconds(60);

    private final Cache<String, Integer> requestCountsCache;

    public RateLimiterService() {
        this.requestCountsCache = Caffeine.newBuilder()
                .expireAfterWrite(WINDOW_TIME)
                .maximumSize(10_000)
                .build();
    }

    public void tryAcquire(HttpServletRequest request) {
        String clientIp = getClientIp(request);

        int currentCount = requestCountsCache.asMap().compute(clientIp,
                (key, count) -> (count == null) ? 1 : count + 1);

        if (currentCount > MAX_REQUESTS) {
            throw new ResponseStatusException(
                    HttpStatus.TOO_MANY_REQUESTS,
                    "Rate limit exceeded. Try again in 60 seconds."
            );
        }
    }

    private String getClientIp(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader != null && !xForwardedForHeader.isEmpty() && !"unknown".equalsIgnoreCase(xForwardedForHeader)) {
            return xForwardedForHeader.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }

}
