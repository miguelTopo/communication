package co.edu.udistrital.message.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import co.edu.udistrital.message.model.MessageGroup;
import co.edu.udistrital.message.repository.MessageGroupRepository;
import co.edu.udistrital.structure.enums.ResponseSeverity;
import co.edu.udistrital.structure.model.Response;

public class MessageGroupService {

	private final MessageGroupRepository messageGroupRepository;

	@Autowired
	public MessageGroupService(MessageGroupRepository messageGroupRepository) {
		this.messageGroupRepository = messageGroupRepository;
	}

	public boolean save(MessageGroup entity) {
		messageGroupRepository.save(entity);
		return true;
	}

	private Response validMessageGroup(MessageGroup mg) {
		if (mg == null)
			return new Response(ResponseSeverity.WARN, "");
		if (StringUtils.isEmpty(mg.getName()))
			return new Response(ResponseSeverity.WARN, "");
		return new Response(ResponseSeverity.INFO, "");
	}

	public Response saveEntity(MessageGroup entity) {

		Response response = validMessageGroup(entity);
		if (response.getResponseSeverity().equals(ResponseSeverity.WARN))
			return response;
		boolean successSave = save(entity);
		if (successSave) {
			response.setMessage("La operación fue exitosa");
			response.setResponseSeverity(ResponseSeverity.SUCCESS);
		} else {
			response.setMessage("Error en la operación");
			response.setResponseSeverity(ResponseSeverity.ERROR);
		}
		return response;
	}
}
