package co.edu.udistrital.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import co.edu.udistrital.user.repository.UserRepository;

@SpringBootApplication(scanBasePackages = {"hello", "books", "co.edu.udistrital.user.controller", "co.edu.udistrital.contact.controller",
	"co.edu.udistrital.user.service", "co.edu.udistrital.contact.service", "co.edu.udistrital.structure.service"})
@EnableMongoRepositories(basePackages = {"co.edu.udistrital.user.repository", "co.edu.udistrital.contact.repository"})
public class CommunicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunicationApplication.class, args);
	}

}
