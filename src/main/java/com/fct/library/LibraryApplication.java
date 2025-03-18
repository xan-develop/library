package com.fct.library;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LibraryApplication {
	public static void main(String[] args) {
		Logger logger = LoggerFactory.getLogger(LibraryApplication.class);
		logger.info("La app se ha iniciado");
		SpringApplication.run(LibraryApplication.class, args);
	}
}



