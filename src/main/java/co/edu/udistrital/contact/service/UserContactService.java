package co.edu.udistrital.contact.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.contact.model.UserContact;
import co.edu.udistrital.contact.repository.UserContactRepository;

@Service
public class UserContactService {

	private UserContactRepository userContactRepository;

	@Autowired
	public UserContactService(UserContactRepository userContactRepository) {
		this.userContactRepository = userContactRepository;
	}

	// public List<UserContact> findByUserId(String userContactId) {
	// List<UserContact> test =this.userContactRepository.findByUserId(userContactId);
	// return test ;
	// }

	public List<UserContact> findAll() {
		List<UserContact> test = this.userContactRepository.findAll();
		return test;
	}

}
