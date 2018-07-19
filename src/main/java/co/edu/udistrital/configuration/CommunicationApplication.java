package co.edu.udistrital.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import books.Person;
import co.edu.udistrital.contact.controller.UserContactController;
import co.edu.udistrital.contact.model.UserContact;
import co.edu.udistrital.contact.repository.UserContactRepository;
import co.edu.udistrital.contact.service.UserContactService;
import co.edu.udistrital.structure.model.User;
import co.edu.udistrital.structure.service.ResponseService;
import co.edu.udistrital.user.controller.UserController;
import co.edu.udistrital.user.repository.AccountRepository;
import co.edu.udistrital.user.repository.PersonRepository;
import co.edu.udistrital.user.repository.UserRepository;
import co.edu.udistrital.user.service.PersonService;
import co.edu.udistrital.user.service.UserService;

@SpringBootApplication(scanBasePackageClasses = {UserService.class, PersonService.class, UserContactService.class, ResponseService.class,
	UserContactController.class, UserController.class, Person.class, User.class, UserContact.class})
@EnableMongoRepositories(basePackageClasses = {PersonRepository.class, UserRepository.class, UserContactRepository.class, AccountRepository.class})
public class CommunicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunicationApplication.class, args);
	}

}
