package com.optus.apis.textprocessor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class TextProcessorApplication {

	public static void main(String[] args) {
		SpringApplication.run(TextProcessorApplication.class, args);
	}
}
