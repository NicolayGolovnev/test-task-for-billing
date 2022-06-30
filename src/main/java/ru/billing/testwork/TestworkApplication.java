package ru.billing.testwork;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TestworkApplication {
	public static void main(String[] args) {
		SpringApplication.run(TestworkApplication.class, args);
	}
}
