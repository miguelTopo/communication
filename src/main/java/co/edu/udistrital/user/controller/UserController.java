package co.edu.udistrital.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.contact.service.UserContactService;
import co.edu.udistrital.structure.service.ResponseService;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	private final UserContactService userContactService;
	private final ResponseService responseService;

	@Autowired
	public UserController(UserContactService userContactService, ResponseService responseService) {
		this.userContactService = userContactService;
		this.responseService = responseService;
	}


}
