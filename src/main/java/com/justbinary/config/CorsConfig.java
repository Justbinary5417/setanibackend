package com.justbinary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {

        CorsConfiguration config = new CorsConfiguration();

        // ── ALLOWED ORIGINS ──────────────────────────────────────
        // Add your frontend URLs here (local dev + production)
        config.setAllowedOrigins(List.of(
            "http://localhost:3000",       // React dev
            "http://localhost:5500",       // Live Server (VS Code)
            "http://localhost:8080",       // Same-server HTML
            "http://127.0.0.1:5500",       // Live Server alt
            "https://justbinary.com",      // Production domain
            "https://www.justbinary.com"   // Production www
        ));

        // ── ALLOWED HTTP METHODS ─────────────────────────────────
        config.setAllowedMethods(List.of(
            "GET",
            "POST",
            "PUT",
            "PATCH",
            "DELETE",
            "OPTIONS"
        ));

        // ── ALLOWED HEADERS ──────────────────────────────────────
        config.setAllowedHeaders(List.of(
            "Authorization",        // JWT Bearer token
            "Content-Type",         // application/json
            "Accept",
            "Origin",
            "X-Requested-With",
            "Access-Control-Request-Method",
            "Access-Control-Request-Headers"
        ));

        // ── EXPOSED HEADERS ──────────────────────────────────────
        // Headers the browser JS can read from the response
        config.setExposedHeaders(List.of(
            "Authorization",
            "Content-Disposition"
        ));

        // ── CREDENTIALS ──────────────────────────────────────────
        // Must be true to send/receive JWT cookies or Auth headers
        config.setAllowCredentials(true);

        // ── PREFLIGHT CACHE ──────────────────────────────────────
        // Browser caches preflight OPTIONS result for 1 hour
        config.setMaxAge(3600L);

        // ── APPLY TO ALL ROUTES ──────────────────────────────────
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}