package co.edu.udistrital.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import hello.Customer;
import hello.CustomerRepository;

@SpringBootApplication
public class CommunicationApplication implements CommandLineRunner {

	@Autowired
	private CustomerRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(CommunicationApplication.class, args);
	}

	public void run(String ...args){
		repository.deleteAll();
		
		//save a couple of customers
		repository.save(new Customer("Alice", "Smith"));
		repository.save(new Customer("Bob", "Smith"));
		
		//fetch all customers
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		for(Customer c: repository.findAll()){
			System.out.println(c);
		}
		System.out.println();
		
		// fetch an individual customer
		System.out.println("Customer found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByFirstName("Alice"));
		
		System.out.println("Customers found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		for (Customer customer : repository.findByLastName("Smith")) {
			System.out.println(customer);
		}
	}
}
