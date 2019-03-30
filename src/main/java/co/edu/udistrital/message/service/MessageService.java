package co.edu.udistrital.message.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import co.edu.udistrital.common.util.ZyosCDNFTP;
import co.edu.udistrital.common.util.ZyosCDNResource;
import co.edu.udistrital.core.util.Bundle;
import co.edu.udistrital.core.util.DateUtil;
import co.edu.udistrital.message.enums.MessageBundle;
import co.edu.udistrital.message.enums.MessageType;
import co.edu.udistrital.message.model.Message;
import co.edu.udistrital.structure.model.Response;
import co.edu.udistrital.structure.model.User;
import co.edu.udistrital.structure.service.ResponseService;
import co.edu.udistrital.user.service.UserService;

@Service
public class MessageService {

	private final ResponseService responseService;
	private final UserService userService;
	private final ConversationService conversationService;

	@Autowired
	public MessageService(@Lazy ResponseService responseService, @Lazy UserService userService, @Lazy ConversationService conversationService) {
		this.responseService = responseService;
		this.userService = userService;
		this.conversationService = conversationService;
	}


	private Response validMessage(Message message) {
		if (message == null)
			return responseService.errorResponse(MessageBundle.MESSAGE_SEND_MESSAGE, MessageBundle.MESSAGE_INVALID_MESSAGE);

		if (message.getReceiverUser() == null || StringUtils.isEmpty(message.getReceiverUser().getId()))
			return responseService.warnResponse(MessageBundle.MESSAGE_SEND_MESSAGE, MessageBundle.MESSAGE_INVALID_RECEIVER);
		if (message.getSenderUser() == null || StringUtils.isEmpty(message.getSenderUser().getId()))
			return responseService.warnResponse(MessageBundle.MESSAGE_SEND_MESSAGE, MessageBundle.MESSAGE_INVALID_SENDER);

		User user = userService.findById(message.getReceiverUser().getId());
		if (user == null)
			return responseService.warnResponse(MessageBundle.MESSAGE_SEND_MESSAGE, MessageBundle.MESSAGE_RECEIVER_NO_FOUND);
		user = userService.findById(message.getSenderUser().getId());
		if (user == null)
			return responseService.warnResponse(MessageBundle.MESSAGE_SEND_MESSAGE, MessageBundle.MESSAGE_SENDER_NO_FOUND);

		switch (message.getMessageType()) {
			case TEXT:
				if (StringUtils.isEmpty(message.getMessageBody()))
					return responseService.warnResponse(MessageBundle.MESSAGE_SEND_MESSAGE, MessageBundle.MESSAGE_INVALID_MESSAGE_TEXT);
			break;
			case AUDIO:
			case VIDEO:
				if (message.getMultipartFile() == null)
					return responseService.warnResponse(MessageBundle.MESSAGE_SEND_MESSAGE, MessageBundle.MESSAGE_INVALID_FILE_TYPE);
			break;
			default:
			break;
		}
		return responseService.successResponse(MessageBundle.MESSAGE_SEND_MESSAGE, Bundle.CORE_SUCCESS_VALIDATION, true);
	}

	private ZyosCDNResource getBasicCDNResource() {
		ZyosCDNResource resource = new ZyosCDNResource();
		resource.setIdEnterprise(10L);
		resource.setFunctionality("communication");
		resource.setDocument(true);
		return resource;
	}


	private Response sendAudioOrVideo(Message message) {
		if (message == null)
			return responseService.errorResponse(MessageBundle.MESSAGE_SEND_MESSAGE, MessageBundle.MESSAGE_INVALID_MESSAGE);
		// Almacenando archivo en servidor Sprint
		try {
			String fileName = message.getMultipartFile().getOriginalFilename();
			// this.fileSystemStorageService.store(message.getMultipartFile());
			//
			// Resource springResource = this.fileSystemStorageService.loadAsResource(fileName);
			// File file = springResource.getFile();
			ZyosCDNResource resource = getBasicCDNResource();
			String ext = fileName.substring(fileName.lastIndexOf('.'), fileName.length());
			resource.setFileName(ZyosCDNFTP.getFileName(ext));
			resource.setInputStream(message.getMultipartFile().getInputStream());
			ZyosCDNFTP.uploadFileResource(resource);
			message.setFile(ZyosCDNFTP.URI_HOST_MAIN + resource.getDatabasePath());
			sendMessage(message);
			return responseService.successResponse(MessageBundle.MESSAGE_SEND_MESSAGE, MessageBundle.MESSAGE_SUCCESS_SEND, true);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}


	@Transactional
	public Response sendAudio(Message message) {
		message.setMessageType(MessageType.AUDIO);
		Response validMessage = validMessage(message);
		if (!validMessage.isBooleanResponse())
			return validMessage;
		return sendAudioOrVideo(message);
	}

	@Transactional
	public Response sendVideo(Message message) {
		message.setMessageType(MessageType.VIDEO);
		Response validMessage = validMessage(message);
		if (!validMessage.isBooleanResponse())
			return validMessage;
		return sendAudioOrVideo(message);
	}

	private void sendMessage(Message message) {
		try {
			if (message == null)
				return;
			// Almacanenando en la colección Message, donde se insertan los mensajes
			message.setSenderUserId(message.getSenderUser().getId());
			message.setReceiverUserId(message.getReceiverUser().getId());
			message.setCreationDate(DateUtil.getCurrentCalendar());
			this.conversationService.sendMessage(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public Response sendTextMessage(Message message) {
		message.setMessageType(MessageType.TEXT);
		Response validMessage = validMessage(message);
		if (!validMessage.isBooleanResponse())
			return validMessage;

		sendMessage(message);
		return responseService.successResponse(MessageBundle.MESSAGE_SEND_MESSAGE, MessageBundle.MESSAGE_SUCCESS_SEND, true);
	}

	public Response loadTalk(String userId, String userContactId) {
		// Response validUserTalk = validLoadTalk(userId, userContactId);
		// if (!validUserTalk.isBooleanResponse())
		// return validUserTalk;
		// Message rootMessage = findMessageRoot(userId, userContactId);
		// if (rootMessage == null)
		// return responseService.successResponse(MessageBundle.MESSAGE_LOAD_TALK, "Los usuarios
		// existen,
		// pero
		// no han iniciado una conversación",
		// Collections.emptyList());
		// List<MessageRecipient> messageList =
		// messageRecipientService.findMessageTreeTalk(rootMessage.getId());
		// System.out.println(messageList == null);
		// return responseService.successResponse(MessageBundle.MESSAGE_LOAD_TALK, "Conversación cn
		// datos",
		// messageList );
		return responseService.successResponse(MessageBundle.MESSAGE_TALK_LOAD, "Conversación con datos", Collections.emptyList());
	}

	private Response validLoadTalk(String userId, String userContactId) {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(userContactId))
			return responseService.warnResponse(MessageBundle.MESSAGE_TALK_LOAD, MessageBundle.MESSAGE_TALK_INVALID_RECEIVED_OR_SENDER, false);

		User user = userService.findById(userContactId);
		if (user == null)
			return responseService.warnResponse(MessageBundle.MESSAGE_TALK_LOAD, MessageBundle.MESSAGE_RECEIVER_NO_FOUND, false);
		user = userService.findById(userId);
		if (user == null)
			return responseService.warnResponse(MessageBundle.MESSAGE_TALK_LOAD, MessageBundle.MESSAGE_SENDER_NO_FOUND, false);
		return responseService.successResponse(MessageBundle.MESSAGE_TALK_LOAD, Bundle.CORE_SUCCESS_VALIDATION, true);
	}


}
