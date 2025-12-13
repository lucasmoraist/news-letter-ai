package com.lucasmoraist.news_letter_ai.infrastructure.filter;

import com.lucasmoraist.news_letter_ai.domain.exceptions.RateLimiterException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class RateLimiterFilter extends OncePerRequestFilter {

    private final RateLimiterService rateLimiterService;

    private static final String TARGET_PATH = "/api/v1/customer/save";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().equals(TARGET_PATH) && request.getMethod().equalsIgnoreCase("POST")) {
            try {
                rateLimiterService.tryAcquire(request);
            } catch (Exception e) {
                throw new RateLimiterException("Rate limit exceeded. Try again later.", e);
            }
        }

        filterChain.doFilter(request, response);
    }

}
