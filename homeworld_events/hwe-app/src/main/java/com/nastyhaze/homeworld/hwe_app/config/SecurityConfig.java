package com.nastyhaze.homeworld.hwe_app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.server.WebFilter;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    // Custom Filters
    private final @Qualifier("logRequestIdWebFilter") WebFilter logRequestIdWebFilter;
    private final @Qualifier("logRequestWebFilter") WebFilter logRequestWebFilter;

    public SecurityConfig(WebFilter logRequestIdWebFilter, WebFilter logRequestWebFilter) {
        this.logRequestIdWebFilter = logRequestIdWebFilter;
        this.logRequestWebFilter = logRequestWebFilter;
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
            .addFilterAt(logRequestIdWebFilter, SecurityWebFiltersOrder.FIRST)
            .addFilterAfter(logRequestWebFilter, SecurityWebFiltersOrder.LAST)
            .csrf(ServerHttpSecurity.CsrfSpec::disable)
            .cors(Customizer.withDefaults())
            .headers(h -> h.frameOptions(ServerHttpSecurity.HeaderSpec.FrameOptionsSpec::disable))
            .authorizeExchange(ex -> ex
                .pathMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .pathMatchers("/", "/index.html", "/assets/**", "/*.js", "/*.css", "/favicon.ico").permitAll()
                .pathMatchers("/api/**").permitAll()
                .anyExchange().permitAll()
            )
            .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration cors = new CorsConfiguration();

        // In DEV, allow all. Prefer whitelisting Angular origin:
        cors.setAllowedOrigins(List.of("http://localhost:4200"));
        cors.setAllowedMethods(List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        cors.setAllowedHeaders(List.of("*"));
        cors.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);

        return source;
    }
}
