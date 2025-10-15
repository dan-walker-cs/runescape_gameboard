package com.nastyhaze.homeworld.hwe_app.config.filter;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.WebFilter;

import java.net.URI;

/**
 *  Configures the logging strategy for HTTP Requests.
 */
@Configuration
public class LogRequestConfig {

    public static final String HTTP_REQUEST_LOGGER_NAME = "HWE_HTTP_REQUEST";
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(HTTP_REQUEST_LOGGER_NAME);

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public WebFilter logRequestWebFilter() {
        return (exchange, chain) -> {
            long startNanos = System.nanoTime();

            final ServerHttpRequest request = exchange.getRequest();
            final URI requestUri = request.getURI();
            final String requestPath = requestUri.getPath();

            final HttpMethod httpMethod = request.getMethod();
            final String methodName = httpMethod.name();

            return chain.filter(exchange)
                .doOnSuccess(ignored -> {
                    final long ms = (System.nanoTime() - startNanos) / 1_000_000;
                    final int status = exchange.getResponse().getStatusCode() == null
                        ? 0
                        : exchange.getResponse().getStatusCode().value();
                    log.info("method={} path={} status={} duration_ms={}",
                        methodName, requestPath, status, ms);
                })
                .doOnError(t -> {
                    final long ms = (System.nanoTime() - startNanos) / 1_000_000;
                    final int status = exchange.getResponse().getStatusCode() == null
                        ? 0
                        : exchange.getResponse().getStatusCode().value();
                    log.error("method={} path={} status={} duration_ms={} error={}",
                        methodName, requestPath, status, ms, t.toString());
                })
                .doOnCancel(() -> {
                    final long ms = (System.nanoTime() - startNanos) / 1_000_000;
                    log.warn("method={} path={} CANCELLED duration_ms={}",
                        methodName, requestPath, ms);
                });
        };
    }
}
