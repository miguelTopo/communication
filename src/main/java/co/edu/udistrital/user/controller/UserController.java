package co.edu.udistrital.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.structure.model.Response;
import co.edu.udistrital.structure.service.ResponseService;
import co.edu.udistrital.user.service.UserService;

@RestController
@RequestMapping(path = "/user")
public class UserController {

	private final UserService userService;
	private final ResponseService responseService;

	@Autowired
	public UserController(UserService userService, ResponseService responseService) {
		this.userService = userService;
		this.responseService = responseService;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/userInDB")
	public ResponseEntity<Response> userExistInDB(@RequestParam String mobilePhone) {
		if (StringUtils.isEmpty(mobilePhone))
			return ResponseEntity.ok().body(
				responseService.errorResponse("Usuario", "No fue posible consultar en la base de datos. Debe proporcionar un número celular", true));
		boolean userInDb = userService.userActiveInDB(mobilePhone);
		String message = userInDb ? "El usuario existe en la base de datos" : "El usuario NO existe en la base de datos";
		return ResponseEntity.ok().body(responseService.infoResponse("Usuario", message, userInDb));
	}


}
