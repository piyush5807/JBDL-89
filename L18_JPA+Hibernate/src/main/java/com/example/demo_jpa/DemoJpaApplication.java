package com.example.demo_jpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

@SpringBootApplication
@ComponentScan(
        basePackages = {"com.example.demo_jpa", "demo"}
)
public class DemoJpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoJpaApplication.class, args);

        /**
         * Different query language types: JPQL and native sql query ==> Custom queries without using any in built jpa repository function
         * Simple JPA repository's save and some other functions
         * Model Minor Project 1 entities like student, transaction, author
         * Development of Minor Project
         *
         * ---
         * Unit testing
         *
         *
         */
    }
}
