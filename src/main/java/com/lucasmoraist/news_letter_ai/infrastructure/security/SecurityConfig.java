package com.lucasmoraist.news_letter_ai.infrastructure.security;

import com.lucasmoraist.news_letter_ai.infrastructure.filter.RateLimiterFilter;
import com.lucasmoraist.news_letter_ai.infrastructure.security.properties.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AppProperties appProperties;
    private final RateLimiterFilter rateLimiterFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers
                        .xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK)) // XSS Protection
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::deny) // Clickjacking Protection
                        .contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'")) // CSP básico
                        .httpStrictTransportSecurity(hsts -> hsts.maxAgeInSeconds(31536000).includeSubDomains(true)) // HSTS
                )
                .authorizeHttpRequests(auth -> Optional
                        .ofNullable(appProperties.getSecurityIgnore())
                        .map(Map::values)
                        .filter(x -> !x.isEmpty())
                        .map(x -> x.toArray(new String[0]))
                        .ifPresentOrElse(
                                it -> auth.requestMatchers(it).permitAll().anyRequest().authenticated(),
                                () -> auth.anyRequest().authenticated()
                        )
                )
                .addFilterBefore(rateLimiterFilter, SecurityContextHolderFilter.class)
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        List<String> allowedOrigins = Optional.ofNullable(appProperties.getAllowedOrigins())
                .orElse(List.of());

        List<String> cleanOrigins = allowedOrigins.stream()
                .map(origin -> origin.endsWith("/") ? origin.substring(0, origin.length() - 1) : origin)
                .toList();

        configuration.setAllowedOrigins(cleanOrigins);
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setExposedHeaders(List.of("Authorization"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
