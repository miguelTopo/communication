package co.edu.udistrital.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.structure.model.User;
import co.edu.udistrital.user.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

}
