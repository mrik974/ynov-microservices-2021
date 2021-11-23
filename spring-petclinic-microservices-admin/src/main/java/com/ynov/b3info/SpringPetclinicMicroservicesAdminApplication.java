package com.ynov.b3info;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import de.codecentric.boot.admin.server.config.EnableAdminServer;

@SpringBootApplication
@EnableAdminServer
public class SpringPetclinicMicroservicesAdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringPetclinicMicroservicesAdminApplication.class, args);
	}

}
