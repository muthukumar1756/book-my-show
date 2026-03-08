package org.cine.launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main Spring Boot application entrypoint.
 */
@SpringBootApplication
@ComponentScan(basePackages = {"org.cine"})
public class BookingConfiguration {
    public static void main(String[] args) {
        SpringApplication.run(BookingConfiguration.class, args);
    }
}