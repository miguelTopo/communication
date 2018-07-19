package co.edu.udistrital.contact.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import books.Person;
import co.edu.udistrital.contact.model.UserContact;
import co.edu.udistrital.contact.service.UserContactService;
import co.edu.udistrital.structure.model.Response;
import co.edu.udistrital.structure.model.User;
import co.edu.udistrital.structure.service.ResponseService;
import co.edu.udistrital.user.service.PersonService;
import co.edu.udistrital.user.service.UserService;

@RestController
@RequestMapping(path = "/userContact")
public class UserContactController {

	private final UserContactService userContactService;
	private final UserService userService;
	private final PersonService personService;
	private final ResponseService responseService;

	@Autowired
	public UserContactController(UserContactService userContactService, UserService userService, PersonService personService,
		ResponseService responseService) {
		this.userContactService = userContactService;
		this.userService = userService;
		this.personService = personService;
		this.responseService = responseService;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/list")
	public ResponseEntity<Response> list(@RequestBody User user) {
		if (user == null || StringUtils.isEmpty(user.getId()))
			return ResponseEntity.ok().body(responseService.warnMessage("Listado de contactos", "No se ha suministrado un usuario válido"));
		List<UserContact> userContactList = this.userContactService.findAll();
		List<User> userList = this.userService.findAll();
		System.out.println("Esto es null" + userList == null);
//		this.personService.saveCosaLoca();
		List<Person> personList = this.personService.findAll();
		System.out.println("Esto es null" + personList == null);
		return ResponseEntity.ok().body(responseService.successResponse("Listado de contactos", "OK", userContactList));
	}
}
