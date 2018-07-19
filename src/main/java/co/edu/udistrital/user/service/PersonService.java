package co.edu.udistrital.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import books.Account;
import books.Person;
import co.edu.udistrital.user.repository.AccountRepository;
import co.edu.udistrital.user.repository.PersonRepository;

@Service
public class PersonService {

	private final PersonRepository personRepository;
	private final AccountRepository accountRepository;

	public PersonService(PersonRepository personRepository, AccountRepository accountRepository) {
		this.personRepository = personRepository;
		this.accountRepository = accountRepository;
	}

	public List<Person> findAll() {
		return this.personRepository.findAll();
	}

	public void saveCosaLoca() {
		Person person = new Person();
		person.setSsn(1234567);
		person.setId(new ObjectId(new Date(), 5));
		List<Account> accountList = new ArrayList<>(3);
		accountList.add(new Account(new ObjectId(new Date(), 2), 350f));
		accountList.add(new Account(new ObjectId(new Date(), 10), 500f));
		accountList.add(new Account(new ObjectId(new Date(), 13), 1000f));
		person.setAccounts(accountList);

		this.personRepository.save(person);
		person.getAccounts().stream().forEach(a -> accountRepository.save(a));
	}

}
