package co.edu.udistrital.structure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.structure.service.UserLangPeferencesService;

@RestController
@RequestMapping(path = "/userLangPeferences")
public class UserLangPeferencesController {

	private final UserLangPeferencesService userLangPeferencesService;

	@Autowired
	public UserLangPeferencesController(UserLangPeferencesService userLangPeferencesService) {
		this.userLangPeferencesService = userLangPeferencesService;
	}

}
