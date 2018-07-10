package co.edu.udistrital.contact.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.contact.model.UserContact;
import co.edu.udistrital.contact.repository.UserContactRepository;

@Service
public class UserContactService {

	@Autowired
	private UserContactRepository userContactRepository;

	public List<UserContact> findByUserContactId(String userContactId) {
		return userContactRepository.findByUserContactId(userContactId);
	}

}
