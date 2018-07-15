package co.edu.udistrital.message.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.structure.model.User;

@Document(collection = "MessageRecipient")
public class MessageRecipient {

	@Id private String id;
	private String recipientId;
	private User recipientUser;
	private String recipientMessageGroupId;
	private MessageGroup recipientMessageGroup;
	private String messageId;
	private Message message;
	private boolean isRead;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRecipientId() {
		return recipientId;
	}

	public void setRecipientId(String recipientId) {
		this.recipientId = recipientId;
	}

	public User getRecipientUser() {
		return recipientUser;
	}

	public void setRecipientUser(User recipientUser) {
		this.recipientUser = recipientUser;
	}

	public String getRecipientMessageGroupId() {
		return recipientMessageGroupId;
	}

	public void setRecipientMessageGroupId(String recipientMessageGroupId) {
		this.recipientMessageGroupId = recipientMessageGroupId;
	}

	public MessageGroup getRecipientMessageGroup() {
		return recipientMessageGroup;
	}

	public void setRecipientMessageGroup(MessageGroup recipientMessageGroup) {
		this.recipientMessageGroup = recipientMessageGroup;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}


}
