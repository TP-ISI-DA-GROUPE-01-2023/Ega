package Ega.api.client;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import Ega.api.client.Entities.Account;
import Ega.api.client.Entities.Client;
import Ega.api.client.Repositories.AccountRepository;
import Ega.api.client.Repositories.ClientRepository;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

	@Bean
	CommandLineRunner start(ClientRepository clientRepository, AccountRepository accountRepository) {
		return args -> {
			Client client = clientRepository
					.save(new Client(1, "GAMLIGO", "Charles", LocalDate.of(2000, 3, 3), "Man", "Sanguera",
							"+228 91611135", "gamligocharles@gmail.com", "Togolease", null));
			Account accountOne = new Account(1, "adtd", "Epargne", LocalDate.of(2023, 3, 3), 120000.0, client);
			accountOne.generateAccountNumber();
			accountRepository.save(accountOne);
			Account accountTwo = new Account(2, "esdd", "Courant", LocalDate.of(2023, 3, 3), 1200000.0, client);
			accountTwo.generateAccountNumber();
			accountRepository.save(accountTwo);
		};
	}

}
