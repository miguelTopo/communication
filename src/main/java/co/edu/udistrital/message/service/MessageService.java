package co.edu.udistrital.message.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.assertj.core.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import co.edu.udistrital.common.util.ZyosCDNFTP;
import co.edu.udistrital.common.util.ZyosCDNResource;
import co.edu.udistrital.core.util.Bundle;
import co.edu.udistrital.core.util.CoreConst;
import co.edu.udistrital.core.util.DateUtil;
import co.edu.udistrital.message.enums.MessageBundle;
import co.edu.udistrital.message.enums.MessageType;
import co.edu.udistrital.message.model.Conversation;
import co.edu.udistrital.message.model.Message;
import co.edu.udistrital.rest.message.model.MessageRest;
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

	private char getMessageTpeForResponse(MessageType mt) {
		if (mt.equals(MessageType.AUDIO))
			return 'a';
		if (mt.equals(MessageType.BRAILLE))
			return 'b';
		if (mt.equals(MessageType.VIDEO))
			return 'v';
		return 't';
	}



	private MessageRest getMessageRestFromMessage(Message message, User user) {
		if (message == null)
			return null;
		MessageRest mr = new MessageRest();
		mr.setM(message.getMessageBody());
		mr.setU(user.getName());
//		mr.setH(DateUtil.datedDate(message.getCreationDate()));
		mr.setF(StringUtils.isEmpty(message.getFile()) ? "not"
			: message.getFile().substring(message.getFile().lastIndexOf('/') + 1, message.getFile().length()));
		mr.setMt(getMessageTpeForResponse(message.getMessageType()));
		return mr;
	}

	private MessageRest parseToMessageRest(Message message) {
		if (message == null)
			return new MessageRest();
		User user = this.userService.findById(message.getSenderUserId());
		return getMessageRestFromMessage(message, user);
	}

	// private List<MessageRest> parseToMessageRest(List<Message> messageList) {
	// if (CollectionUtils.isEmpty(messageList))
	// return Collections.emptyList();
	// List<String> userIdList = messageList.stream().map(m ->
	// m.getSenderUserId()).collect(Collectors.toList());
	// List<User> userList = this.userService.findByIdIn(userIdList);
	// List<MessageRest> messageRestList = new ArrayList<>(messageList.size());
	// User user = null;
	// for (Message m : messageList) {
	// user = userList.stream().filter(u ->
	// u.getId().equals(m.getSenderUserId())).findFirst().orElse(new User());
	// messageRestList.add(getMessageRestFromMessage(m, user));
	// }
	// return messageRestList;
	// }


	/*
	 * public List<MessageRest> homeMessage(String homeUserId) { List<Conversation> conversationList
	 * = conversationService.findByHomeUserId(homeUserId); if
	 * (CollectionUtils.isEmpty(conversationList)) return Collections.emptyList(); List<Message>
	 * messageList = null;
	 * 
	 * for (Conversation c : conversationList) { for (Message m : c.getMessageList()) { if
	 * (CollectionUtils.isEmpty(m.getReadUserIdList()) ||
	 * !m.getReadUserIdList().contains(homeUserId)) { if (messageList == null) messageList = new
	 * ArrayList<>(1); messageList.add(m); } } } return parseToMessageRest(messageList); }
	 */

	public MessageRest homeMessage(String homeUserId) {
		List<Conversation> conversationList = conversationService.findByHomeUserId(homeUserId);
		if (CollectionUtils.isEmpty(conversationList))
			return new MessageRest();
		MessageRest messageRest = null;
		Message firstUnreadMessage = null;

		label: for (Conversation c : conversationList) {
			if (CollectionUtils.isEmpty(c.getMessageList()))
				continue;
			for (Message m : c.getMessageList()) {
				if (CollectionUtils.isEmpty(m.getReadUserIdList()) || !m.getReadUserIdList().contains(homeUserId)) {
					if (m.getReadUserIdList() == null)
						m.setReadUserIdList(new ArrayList<>(1));
					m.getReadUserIdList().add(homeUserId);
					firstUnreadMessage = m;
					break label;
				}
			}
		}
		if (firstUnreadMessage != null) {
			messageRest = parseToMessageRest(firstUnreadMessage);
			this.conversationService.saveAll(conversationList);
		}
		return messageRest;
	}

}
