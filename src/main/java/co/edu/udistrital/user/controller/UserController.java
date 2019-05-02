package co.edu.udistrital.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import co.edu.udistrital.core.util.CoreConst;
import co.edu.udistrital.core.util.JsonUtil;
import co.edu.udistrital.structure.model.Response;
import co.edu.udistrital.structure.model.User;
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
			return ResponseEntity.ok().body(responseService.errorResponse("Consultar usuario",
				"No fue posible consultar en la base de datos. Debe proporcionar un número celular", false));
		boolean userInDb = userService.userActiveInDB(mobilePhone);
		String message = userInDb ? "El usuario existe en la base de datos" : "El usuario NO existe en la base de datos";
		return ResponseEntity.ok().body(responseService.successResponse("Usuario", message, userInDb));
	}

	@RequestMapping(method = RequestMethod.POST, path = "/updateLanPreferece")
	public ResponseEntity<Response> updateLanPreferece(@RequestBody User user) {
		if (user == null)
			return ResponseEntity.ok().body(
				responseService.errorResponse("Actualizar preferencias", "Ocurrió un error, intente nuevamente o consulte al administrador", false));
		return userService.updateLanPreferece(user);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/userLanPreferece")
	public String getUserLanPreferece(@RequestParam("userId") String userId) {
		if (StringUtils.isEmpty(userId))
			return CoreConst.STRING_EMPTY;
		return userService.getUserLanPreferece(userId);
	}

	@RequestMapping(method = RequestMethod.POST, path = "/userSingleRegister")
	public ResponseEntity<Response> userSingleRegister(@RequestBody User user) {
		if (user == null)
			return ResponseEntity.ok()
				.body(responseService.warnMessage("Registrar usuario", "Ocurrió un error, intente nuevamente o consulte al administrador"));
		return userSingleRegister(user, null);
	}

	@PostMapping(value = "/multipartUserSingleRegister", consumes = "multipart/form-data")
	public ResponseEntity<Response> userSingleRegister(@RequestParam("file") MultipartFile file, @RequestParam("jsonData") String jsonData) {
		if (file == null || jsonData == null)
			return ResponseEntity.ok()
				.body(responseService.warnMessage("Registrar usuario", "Ocurrió un error, intente nuevamente o consulte al administrador"));
		User user = (User) JsonUtil.asObject(jsonData, User.class);
		return userSingleRegister(user, file);
	}

	private ResponseEntity<Response> userSingleRegister(User user, MultipartFile file) {
		if (user == null)
			return ResponseEntity.ok()
				.body(responseService.warnMessage("Registrar usuario", "Ocurrió un error, intente nuevamente o consulte al administrador"));
		user = this.userService.userSingleRegister(user, file);
		boolean success = user != null;
		Response response = new Response();
		response.setEntity(user);
		response.setBooleanResponse(success);
		if (success)
			return ResponseEntity.ok().body(responseService.successResponse("Registrar de usuario", "El usuario se registró exitosamente", response));
		else
			return ResponseEntity.ok().body(
				responseService.warnResponse("Registrar usuario", "Ocurrió un error, intente nuevamente o consulte al administrador", response));
	}

}
