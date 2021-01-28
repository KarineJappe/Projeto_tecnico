package com.projetoTecnico.projetoTecnico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan


public class ProjetoTecnicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProjetoTecnicoApplication.class, args);
	}

}
