package com.proyectoSo.proyecto_so;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ProyectoSoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProyectoSoApplication.class, args);
	}

}
