package co.edu.udistrital.message.controller;

import java.util.List;

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

import co.edu.udistrital.core.util.JsonUtil;
import co.edu.udistrital.message.model.Message;
import co.edu.udistrital.message.service.MessageService;
import co.edu.udistrital.structure.model.Response;
import co.edu.udistrital.structure.model.User;
import co.edu.udistrital.structure.service.ResponseService;

@RestController
@RequestMapping("/message")
public class MessageController {

	private final MessageService messageService;
	private final ResponseService responseService;

	@Autowired
	public MessageController(MessageService messageService, ResponseService responseService) {
		this.messageService = messageService;
		this.responseService = responseService;
	}

	@RequestMapping(method = RequestMethod.POST, path = "/talkRequest")
	public String talkRequest(@RequestBody User user) {
		if (user == null)
			return "";
		else
			return "success";

	}

	@RequestMapping(method = RequestMethod.POST, path = "/sendText")
	public ResponseEntity<Response> sendText(@RequestBody Message message) {
		if (message == null)
			return ResponseEntity.ok()
				.body(responseService.warnMessage("Enviar mensaje", "Ocurrió un error, intente nuevamente o consulte al administrador"));
		return ResponseEntity.ok().body(this.messageService.sendTextMessage(message));
	}

	@RequestMapping(method = RequestMethod.POST, path = "/talk")
	public ResponseEntity<Response> loadTalk(@RequestParam String userId, @RequestParam String userContactId) {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(userContactId))
			return ResponseEntity.ok()
				.body(responseService.warnMessage("Cargar conversación", "Ocurrió un error, intente nuevamente o consulte al administrador"));
		return ResponseEntity.ok().body(this.messageService.loadTalk(userId, userContactId));
	}

	@PostMapping(value = "/sendAudio", consumes = "multipart/form-data")
	public ResponseEntity<Response> sendAudio(@RequestParam("file") MultipartFile file, @RequestParam("jsonData") String jsonData) {
		if (file == null || StringUtils.isEmpty(jsonData))
			return ResponseEntity.ok()
				.body(responseService.warnMessage("Enviar mensaje", "Ocurrió un error, intente nuevamente o consulte al administrador"));
		Message message = (Message) JsonUtil.asObject(jsonData, Message.class);
		message.setMultipartFile(file);
		return ResponseEntity.ok().body(this.messageService.sendAudio(message));
	}

	@PostMapping(value = "/sendVideo", consumes = "multipart/form-data")
	public ResponseEntity<Response> sendVideo(@RequestParam("file") MultipartFile file, @RequestParam("jsonData") String jsonData) {
		if (file == null || StringUtils.isEmpty(jsonData))
			return ResponseEntity.ok()
				.body(responseService.warnMessage("Enviar mensaje", "Ocurrió un error, intente nuevamente o consulte al administrador"));
		Message message = (Message) JsonUtil.asObject(jsonData, Message.class);
		message.setMultipartFile(file);
		return ResponseEntity.ok().body(this.messageService.sendVideo(message));
	}

	@RequestMapping(method = RequestMethod.POST, path = "/list")
	public ResponseEntity<Response> testList() {
		List<Message> messageList = this.messageService.findAll();
		return ResponseEntity.ok().body(responseService.successResponse("", "", messageList));
	}
	
	@RequestMapping(method = RequestMethod.POST, path = "/test")	
	public ResponseEntity<Response> test() {
		boolean success = this.messageService.saveOne();
		return ResponseEntity.ok().body(responseService.successResponse("", "", success));
	}

}
