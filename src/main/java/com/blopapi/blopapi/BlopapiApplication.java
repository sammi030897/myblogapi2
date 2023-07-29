package com.blopapi.blopapi;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BlopapiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlopapiApplication.class, args);
	}

	@Bean
	public ModelMapper modelmapper() {
		return new ModelMapper();
	}
}
