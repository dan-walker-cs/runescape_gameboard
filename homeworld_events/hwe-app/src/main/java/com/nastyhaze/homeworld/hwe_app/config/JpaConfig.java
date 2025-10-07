package com.nastyhaze.homeworld.hwe_app.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * This configuration class exists to enable automatic auditing on JPA entities via the domain.AuditEntity object.
 */
@Configuration
@EnableJpaAuditing // (optional) modifyOnCreate=true by default
class JpaConfig {}
