package co.edu.udistrital.contact.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.contact.model.UserContact;
import co.edu.udistrital.contact.service.UserContactService;

@RestController
public class ContactController {

	@Autowired
	private UserContactService userContactService;

	@RequestMapping(method = RequestMethod.POST, path = "/contactList")
	public ResponseEntity<List<UserContact>> list(@RequestAttribute("userId") String userId) {
		List<UserContact> userContactList = userContactService.findByUserContactId(userId);
		return ResponseEntity.ok().body(userContactList);
	}

}
