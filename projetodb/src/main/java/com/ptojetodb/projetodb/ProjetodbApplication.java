package com.ptojetodb.projetodb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ProjetodbApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProjetodbApplication.class, args);
	}

}
