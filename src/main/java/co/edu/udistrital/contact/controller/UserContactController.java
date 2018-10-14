package co.edu.udistrital.contact.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.contact.service.UserContactService;
import co.edu.udistrital.structure.model.Response;
import co.edu.udistrital.structure.model.User;
import co.edu.udistrital.structure.service.ResponseService;

@RestController
@RequestMapping(path = "/userContact")
public class UserContactController {

	private final UserContactService userContactService;
	private final ResponseService responseService;

	@Autowired
	public UserContactController(UserContactService userContactService, ResponseService responseService) {
		this.userContactService = userContactService;
		this.responseService = responseService;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/list")
	public ResponseEntity<Response> getContactList(@RequestBody User user) {
		if (user == null || StringUtils.isEmpty(user.getMobilePhone()))
			return ResponseEntity.ok().body(responseService.warnMessage("Listado de contactos", "No se ha suministrado un usuario válido"));
		return ResponseEntity.ok()
			.body(responseService.successResponse("Listado de contactos", "OK", this.userContactService.findContactListByUser(user)));
	}
}
