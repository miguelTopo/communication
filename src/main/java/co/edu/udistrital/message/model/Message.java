package co.edu.udistrital.message.model;

import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import co.edu.udistrital.common.model.CoreEntity;
import co.edu.udistrital.message.enums.MessageType;
import co.edu.udistrital.structure.model.User;


public class Message extends CoreEntity {

	public Message() {}

	private MultipartFile multipartFile;

	private String senderUserId;

	private String receiverUserId;

	private String messageBody;

	private String file;

	@Transient private User senderUser;

	@Transient private User receiverUser;

	private MessageType messageType;

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public MultipartFile getMultipartFile() {
		return multipartFile;
	}

	public void setMultipartFile(MultipartFile multipartFile) {
		this.multipartFile = multipartFile;
	}

	public String getFile() {
		return file;
	}

	public void setFile(String file) {
		this.file = file;
	}

	public String getSenderUserId() {
		return senderUserId;
	}

	public void setSenderUserId(String senderUserId) {
		this.senderUserId = senderUserId;
	}

	public User getSenderUser() {
		return senderUser;
	}

	public void setSenderUser(User senderUser) {
		this.senderUser = senderUser;
	}

	public User getReceiverUser() {
		return receiverUser;
	}

	public void setReceiverUser(User receiverUser) {
		this.receiverUser = receiverUser;
	}

	public String getReceiverUserId() {
		return receiverUserId;
	}

	public void setReceiverUserId(String receiverUserId) {
		this.receiverUserId = receiverUserId;
	}

}
