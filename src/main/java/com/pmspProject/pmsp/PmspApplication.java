/**
 * PmspApplication class is the main entry point of the application.
 * It uses Spring Boot's @SpringBootApplication annotation to enable auto-configuration, component scanning,
 * and other features.
 *
 * @author uday
 * @since 1.0.0
 */
package com.pmspProject.pmsp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PmspApplication {

	public static void main(String[] args) {
		SpringApplication.run(PmspApplication.class, args);
	}

}
