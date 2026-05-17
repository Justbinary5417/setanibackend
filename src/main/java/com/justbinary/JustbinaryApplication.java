package com.justbinary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.justbinary.model")
@EnableJpaRepositories(basePackages = "com.justbinary.repository")
public class JustbinaryApplication {

    public static void main(String[] args) {
        SpringApplication.run(JustbinaryApplication.class, args);
    }
}