package co.edu.udistrital.message.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.message.model.MessageGroup;
import co.edu.udistrital.message.service.MessageGroupService;
import co.edu.udistrital.structure.model.Response;

@RestController
@RequestMapping("/messageGroupRequest")
public class MessageGroupController {

	private final MessageGroupService messageGroupService;

	@Autowired
	public MessageGroupController(MessageGroupService messageGroupService) {
		this.messageGroupService = messageGroupService;
	}

	@RequestMapping("/save")
	public Response save(@RequestBody MessageGroup mg) {
		Response response = new Response();
		try {
			if (mg == null)
				return response;
			response = messageGroupService.saveEntity(mg);
			return response ;
		} catch (Exception e) {
			return response.errorResponse(mg);
		}
	}
}