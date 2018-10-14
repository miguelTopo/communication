package co.edu.udistrital.configuration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.MongoClient;

import co.edu.udistrital.contact.controller.UserContactController;
import co.edu.udistrital.contact.model.UserContact;
import co.edu.udistrital.contact.repository.UserContactRepository;
import co.edu.udistrital.contact.service.UserContactService;
import co.edu.udistrital.core.service.FileSystemStorageService;
import co.edu.udistrital.message.controller.MessageController;
import co.edu.udistrital.message.model.MessageRecipient;
import co.edu.udistrital.message.repository.MessageRecipientRepository;
import co.edu.udistrital.message.repository.MessageRepository;
import co.edu.udistrital.message.service.MessageRecipientService;
import co.edu.udistrital.message.service.MessageService;
import co.edu.udistrital.structure.model.User;
import co.edu.udistrital.structure.service.ResponseService;
import co.edu.udistrital.user.controller.UserController;
import co.edu.udistrital.user.repository.UserRepository;
import co.edu.udistrital.user.service.UserService;

@SpringBootApplication(
	scanBasePackageClasses = {
		UserService.class, 
		UserContactService.class, 
		ResponseService.class, 
		MessageService.class, 
		MessageRecipientService.class,
		FileSystemStorageService.class,
		UserContactController.class, 
		UserController.class, 
		MessageController.class, 
		User.class, 
		UserContact.class, 
		MessageRecipient.class
			})
@EnableMongoRepositories(
	basePackageClasses = {
			UserRepository.class, 
			UserContactRepository.class, 
			MessageRepository.class, 
			MessageRecipientRepository.class})
public class CommunicationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommunicationApplication.class, args);
	}

	public @Bean MongoClient mongoClient() {
		return new MongoClient("localhost");
	}

	public @Bean MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), "communication");
	}


}
