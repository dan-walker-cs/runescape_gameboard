package com.nastyhaze.homeworld.hwe_app.config.filter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.WebFilter;

/**
 *  Configures the retrieval & logging strategy for HTTP Request IDs.
 */
@Configuration
public class LogRequestIdConfig {
    public static final String REQUEST_ID_HEADER = "X-Request-Id";
    public static final String REQUEST_ID = "HWE_LOG_REQUEST_ID";

    @Bean
    public WebFilter logRequestIdWebFilter() {
        return (exchange, chain) -> {
            final String incoming = exchange.getRequest().getHeaders().getFirst(REQUEST_ID_HEADER);
            final String logRequestId = (incoming == null || incoming.isBlank())
                ? java.util.UUID.randomUUID().toString()
                : incoming;

            exchange.getResponse().getHeaders().set(REQUEST_ID_HEADER, logRequestId);

            return chain.filter(exchange)
                .contextWrite(ctx -> ctx.put(REQUEST_ID, logRequestId));
        };
    }
}
