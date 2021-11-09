package com.ynov.b3info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class SpringPetclinicMicroservicesPetsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPetclinicMicroservicesPetsApplication.class, args);
	}

}
