package com.example.ampersand02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootApplication
public class Ampersand02Application {

	public static void main(String[] args) {
		SpringApplication.run(Ampersand02Application.class, args);
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}
}
