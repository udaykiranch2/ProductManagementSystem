/**
 * PmspApplication class is the main entry point of the application.
 * It uses Spring Boot's @SpringBootApplication annotation to enable auto-configuration, component scanning,
 * and other features.
 *
 * @author uday
 * @since 1.0.0
 */
package com.pmspProject.pmsp;

import com.pmspProject.pmsp.audit.AuditorAwareImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
public class PmspApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmspApplication.class, args);
	}
	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}
}
