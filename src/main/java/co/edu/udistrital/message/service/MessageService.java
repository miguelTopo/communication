package co.edu.udistrital.message.service;

import java.util.Collections;
import java.util.List;

import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import co.edu.udistrital.core.service.FileSystemStorageService;
import co.edu.udistrital.core.util.Bundle;
import co.edu.udistrital.core.util.DateUtil;
import co.edu.udistrital.message.enums.MessageReadState;
import co.edu.udistrital.message.enums.MessageType;
import co.edu.udistrital.message.model.Message;
import co.edu.udistrital.message.model.MessageRecipient;
import co.edu.udistrital.message.model.Test;
import co.edu.udistrital.message.repository.MessageDAOTest;
import co.edu.udistrital.message.repository.MessageRepository;
import co.edu.udistrital.structure.model.Response;
import co.edu.udistrital.structure.model.User;
import co.edu.udistrital.structure.service.ResponseService;
import co.edu.udistrital.user.service.UserService;

@Service
public class MessageService {

	private final MessageRepository messageRepository;
	private final ResponseService responseService;
	private final UserService userService;
	private final MessageRecipientService messageRecipientService;
	private final FileSystemStorageService fileSystemStorageService;


	private MessageDAOTest messageDAO;


	private MessageDAOTest getMessageDAO() {
		if (this.messageDAO == null)
			this.messageDAO = new MessageDAOTest();
		return this.messageDAO;
	}


	@Autowired
	public MessageService(@Lazy MessageRepository messageRepository, @Lazy ResponseService responseService, @Lazy UserService userService,
		@Lazy MessageRecipientService messageRecipientService, @Lazy FileSystemStorageService fileSystemStorageService) {
		this.messageRepository = messageRepository;
		this.responseService = responseService;
		this.userService = userService;
		this.messageRecipientService = messageRecipientService;
		this.fileSystemStorageService = fileSystemStorageService;
	}


	private Response validMessage(Message message) {
		if (message == null)
			return responseService.errorResponse(Bundle.MESSAGE_SEND_MESSAGE, Bundle.MESSAGE_INVALID_MESSAGE);

		if (message.getReceiverUser() == null || StringUtils.isEmpty(message.getReceiverUser().getId()))
			return responseService.warnResponse(Bundle.MESSAGE_SEND_MESSAGE, Bundle.MESSAGE_INVALID_RECEIVER);
		if (message.getSenderUser() == null || StringUtils.isEmpty(message.getSenderUser().getId()))
			return responseService.warnResponse(Bundle.MESSAGE_SEND_MESSAGE, Bundle.MESSAGE_INVALID_SENDER);

		User user = userService.findById(message.getReceiverUser().getId());
		if (user == null)
			return responseService.warnResponse(Bundle.MESSAGE_SEND_MESSAGE, Bundle.MESSAGE_RECEIVER_NO_FOUND);
		user = userService.findById(message.getSenderUser().getId());
		if (user == null)
			return responseService.warnResponse(Bundle.MESSAGE_SEND_MESSAGE, Bundle.MESSAGE_SENDER_NO_FOUND);

		switch (message.getMessageType()) {
			case TEXT:
				if (StringUtils.isEmpty(message.getMessageBody()))
					return responseService.warnResponse(Bundle.MESSAGE_SEND_MESSAGE, Bundle.MESSAGE_INVALID_MESSAGE_TEXT);
			break;
			case AUDIO:
			case VIDEO:
				if (message.getFile() == null)
					return responseService.warnResponse(Bundle.MESSAGE_SEND_MESSAGE, Bundle.MESSAGE_INVALID_FILE_TYPE);
			break;
			default:
			break;
		}
		return responseService.successResponse(Bundle.MESSAGE_SEND_MESSAGE, Bundle.CORE_SUCCESS_VALIDATION, true);
	}

	private void save(Message message) {
		if (message == null)
			return;
		getMessageDAO().getSession().save(message);
	}

	private boolean saveTx(Message message) {
		if (message == null)
			return false;
		Transaction tx = getMessageDAO().getSession().beginTransaction();
		try {
			save(message);
			// Almacanenando en la colección MessageRecipient. Donde se insertan las relaciones del
			// mensaje
			MessageRecipient messageRecipient = new MessageRecipient();
			messageRecipient.setMessageId(message.getId());
			messageRecipient.setMessageReadState(MessageReadState.SENT);
			messageRecipient.setRecipientUserId(message.getReceiverUser().getId());
			this.messageRecipientService.save(messageRecipient);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			getMessageDAO().getSession().cancelQuery();
			if (tx != null && tx.isActive())
				tx.rollback();
			return false;
		} finally {
			getMessageDAO().getSession().close();
		}
	}

	private Response sendAudioOrVideo(Message message) {
		if (message == null)
			return responseService.errorResponse(Bundle.MESSAGE_SEND_MESSAGE, Bundle.MESSAGE_INVALID_MESSAGE);
		// Almacenando archivo en servidor Sprint
		this.fileSystemStorageService.store(message.getMultipartFile());
		message.setFile(message.getMultipartFile().getName());
		sendMessage(message);
		return responseService.successResponse(Bundle.MESSAGE_SEND_MESSAGE, Bundle.MESSAGE_SUCCESS_SEND, true);
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

	public String getParentMessageId(String senderUserId, String receiverUserId) {
		if (StringUtils.isEmpty(senderUserId) || StringUtils.isEmpty(receiverUserId))
			return "";
		try {
			Message m = getMessageDAO().findParentMessage(senderUserId, receiverUserId);
			if (m == null)
				return null;
			return StringUtils.isEmpty(m.getParentMessageId()) ? null : m.getParentMessageId();
		} catch (Exception e) {
			getMessageDAO().getSession().cancelQuery();
		} finally {
			getMessageDAO().getSession().close();
		}
		return "";
	}

	private void sendMessage(Message message) {
		if (message == null)
			return;
		// Almacanenando en la colección Message, donde se insertan los mensajes
		message.setSenderUserId(message.getSenderUser().getId());
		String messageParentId = getParentMessageId(message.getSenderUser().getId(), message.getReceiverUser().getId());
		message.setParentMessageId(messageParentId);
		message.setCreationDate(DateUtil.getCurrentCalendar());
		saveTx(message);
	}

	@Transactional
	public Response sendTextMessage(Message message) {
		message.setMessageType(MessageType.TEXT);
		Response validMessage = validMessage(message);
		if (!validMessage.isBooleanResponse())
			return validMessage;

		sendMessage(message);
		return responseService.successResponse(Bundle.MESSAGE_SEND_MESSAGE, Bundle.MESSAGE_SUCCESS_SEND, true);
	}

	public Response loadTalk(String userId, String userContactId) {
		// Response validUserTalk = validLoadTalk(userId, userContactId);
		// if (!validUserTalk.isBooleanResponse())
		// return validUserTalk;
		// Message rootMessage = findMessageRoot(userId, userContactId);
		// if (rootMessage == null)
		// return responseService.successResponse(Bundle.MESSAGE_LOAD_TALK, "Los usuarios existen,
		// pero
		// no han iniciado una conversación",
		// Collections.emptyList());
		// List<MessageRecipient> messageList =
		// messageRecipientService.findMessageTreeTalk(rootMessage.getId());
		// System.out.println(messageList == null);
		// return responseService.successResponse(Bundle.MESSAGE_LOAD_TALK, "Conversación cn datos",
		// messageList );
		return responseService.successResponse(Bundle.MESSAGE_TALK_LOAD, "Conversación con datos", Collections.emptyList());
	}

	private Response validLoadTalk(String userId, String userContactId) {
		if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(userContactId))
			return responseService.warnResponse(Bundle.MESSAGE_TALK_LOAD, Bundle.MESSAGE_TALK_INVALID_RECEIVED_OR_SENDER, false);

		User user = userService.findById(userContactId);
		if (user == null)
			return responseService.warnResponse(Bundle.MESSAGE_TALK_LOAD, Bundle.MESSAGE_RECEIVER_NO_FOUND, false);
		user = userService.findById(userId);
		if (user == null)
			return responseService.warnResponse(Bundle.MESSAGE_TALK_LOAD, Bundle.MESSAGE_SENDER_NO_FOUND, false);
		return responseService.successResponse(Bundle.MESSAGE_TALK_LOAD, Bundle.CORE_SUCCESS_VALIDATION, true);
	}


	public List<Message> findAll() {
		try {
			return getMessageDAO().findAll();
		} catch (Exception e) {
			getMessageDAO().getSession().cancelQuery();
			e.printStackTrace();
		} finally {
			getMessageDAO().getSession().close();
		}
		return Collections.emptyList();
	}


	public boolean saveOne() {
		Transaction tx = null;
		try {
			tx = getMessageDAO().getSession().beginTransaction();
			Test t = new Test();
			t.addAuditInfo(true, "dskhdkgfd");
			t.setName("Amiguitos");
			getMessageDAO().getSession().save(t);
			tx.commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			getMessageDAO().getSession().cancelQuery();
			if (tx != null && tx.isActive())
				tx.rollback();
			return false;
		} finally {
			getMessageDAO().getSession().close();
		}
	}


}
