package br.nom.carneiro.carlos.backend_challenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BackendChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendChallengeApplication.class, args);
	}

}
