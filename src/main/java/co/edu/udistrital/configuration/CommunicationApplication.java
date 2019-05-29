package co.edu.udistrital.configuration;


import java.util.Locale;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.mongodb.MongoClient;

import co.edu.udistrital.common.util.controller.ManageFileController;
import co.edu.udistrital.contact.controller.UserContactController;
import co.edu.udistrital.contact.model.UserContact;
import co.edu.udistrital.contact.repository.UserContactRepository;
import co.edu.udistrital.contact.service.UserContactService;
import co.edu.udistrital.core.controller.FileUploadController;
import co.edu.udistrital.core.service.FileSystemStorageService;
import co.edu.udistrital.core.service.StorageService;
import co.edu.udistrital.event.controller.EventController;
import co.edu.udistrital.event.model.Event;
import co.edu.udistrital.event.repository.EventRepository;
import co.edu.udistrital.event.service.EventService;
import co.edu.udistrital.message.controller.MessageController;
import co.edu.udistrital.message.repository.ConversationRepository;
import co.edu.udistrital.message.service.ConversationService;
import co.edu.udistrital.message.service.MessageService;
import co.edu.udistrital.structure.model.User;
import co.edu.udistrital.structure.service.ResponseService;
import co.edu.udistrital.user.controller.UserController;
import co.edu.udistrital.user.repository.UserRepository;
import co.edu.udistrital.user.service.UserService;

@SpringBootApplication(scanBasePackageClasses = {UserService.class, UserContactService.class, ConversationService.class, ResponseService.class,
	MessageService.class, FileSystemStorageService.class, EventService.class, UserContactController.class, UserController.class,
	MessageController.class, User.class, UserContact.class, EventController.class, Event.class, FileUploadController.class, ManageFileController.class})

@EnableMongoRepositories(
	basePackageClasses = {UserRepository.class, UserContactRepository.class, ConversationRepository.class, EventRepository.class})

@EnableConfigurationProperties(StorageProperties.class)
public class CommunicationApplication implements WebMvcConfigurer {

	public static void main(String[] args) {
		SpringApplication.run(CommunicationApplication.class, args);
	}

	@Bean
	public MongoClient mongoClient() {
		return new MongoClient("localhost");
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoClient(), "communication");
	}

	@Bean
	public LocaleResolver localeResolver() {
		SessionLocaleResolver slr = new SessionLocaleResolver();
		Locale locale = new Locale("en");
		slr.setDefaultLocale(locale);
		return slr;
	}

	/**
	 * Método que permite cambiar el locale a devolver desde el servidor cuando el usuario lo
	 * requiere
	 */
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("language");
		return localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.init();
		};
	}

}
