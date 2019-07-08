package co.edu.udistrital.message.controller;

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

import co.edu.udistrital.core.util.Bundle;
import co.edu.udistrital.core.util.JsonUtil;
import co.edu.udistrital.message.enums.MessageBundle;
import co.edu.udistrital.message.model.Message;
import co.edu.udistrital.message.service.MessageService;
import co.edu.udistrital.rest.enums.ResponseType;
import co.edu.udistrital.rest.message.model.MessageResponse;
import co.edu.udistrital.structure.model.Response;
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


	@RequestMapping(method = RequestMethod.POST, path = "/homeMessage")
	public MessageResponse homeMessage(@RequestParam("homeUserId") String homeUserId) {
		System.out.println("Estamos consultando mensajes para el usuario");
		if (StringUtils.isEmpty(homeUserId))
			return new MessageResponse(ResponseType.MSG);
		return this.messageService.homeMessage(homeUserId);
	}


	/*
	 * @RequestMapping(method = RequestMethod.POST, path = "/homeMessage") public List<MessageRest>
	 * homeMessage(@RequestParam("homeUserId") String homeUserId) { if
	 * (StringUtils.isEmpty(homeUserId)) return Collections.emptyList(); return
	 * this.messageService.homeMessage(homeUserId); }
	 */

	@RequestMapping(method = RequestMethod.POST, path = "/sendText2")
	public ResponseEntity<Response> sendText2(@RequestBody Message message) {
		if (message == null)
			return ResponseEntity.ok().body(responseService.warnMessage(MessageBundle.MESSAGE_SEND_MESSAGE, Bundle.CORE_EROR_RESPONSE));
		return ResponseEntity.ok().body(this.messageService.sendTextMessage(message));
	}

	@PostMapping(value = "/sendText", consumes = "multipart/form-data")
	public ResponseEntity<Response> sendText(@RequestParam("file") MultipartFile file, @RequestParam("jsonData") String jsonData) {
		if (file == null || StringUtils.isEmpty(jsonData))
			return ResponseEntity.ok().body(responseService.warnMessage(MessageBundle.MESSAGE_SEND_MESSAGE, Bundle.CORE_EROR_RESPONSE));
		Message message = (Message) JsonUtil.asObject(jsonData, Message.class);
		message.setMultipartFile(file);
		return ResponseEntity.ok().body(this.messageService.sendTextMessage(message));
	}

	@PostMapping(value = "/sendAudio", consumes = "multipart/form-data")
	public ResponseEntity<Response> sendAudio(@RequestParam("file") MultipartFile file, @RequestParam("jsonData") String jsonData) {
		if (file == null || StringUtils.isEmpty(jsonData))
			return ResponseEntity.ok().body(responseService.warnMessage(MessageBundle.MESSAGE_SEND_MESSAGE, Bundle.CORE_EROR_RESPONSE));
		Message message = (Message) JsonUtil.asObject(jsonData, Message.class);
		message.setMultipartFile(file);
		return ResponseEntity.ok().body(this.messageService.sendAudio(message));
	}

	@PostMapping(value = "/sendVideo", consumes = "multipart/form-data")
	public ResponseEntity<Response> sendVideo(@RequestParam("file") MultipartFile file, @RequestParam("jsonData") String jsonData) {
		if (file == null || StringUtils.isEmpty(jsonData))
			return ResponseEntity.ok().body(responseService.warnMessage(MessageBundle.MESSAGE_SEND_MESSAGE, Bundle.CORE_EROR_RESPONSE));
		Message message = (Message) JsonUtil.asObject(jsonData, Message.class);
		message.setMultipartFile(file);
		return ResponseEntity.ok().body(this.messageService.sendVideo(message));
	}
}


