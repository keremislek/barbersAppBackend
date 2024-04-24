package com.example.barbersApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@ComponentScan("com.example.barbersApp")
public class BarbersAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(BarbersAppApplication.class, args);
	}

}
