package com.example.kristp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KristPApplication {

	public static void main(String[] args) {
		SpringApplication.run(KristPApplication.class, args);
	}

}
