package co.edu.udistrital.message.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.message.enums.MessageReadState;
import co.edu.udistrital.structure.model.User;

@Document(collection = "MessageRecipient")
public class MessageRecipient {

	public MessageRecipient() {
		// Basic Empty Constructor
	}

	@Id private String id;

	private String recipientUserId;

	private String recipientMessageGroupId;

	private String messageId;

	private Date readDate;

	@Transient private Message message;

	@Transient private User recipientUser;

	@Transient private MessageGroup recipientMessageGroup;

	private MessageReadState messageReadState;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public String getRecipientUserId() {
		return recipientUserId;
	}

	public void setRecipientUserId(String recipientUserId) {
		this.recipientUserId = recipientUserId;
	}

	public MessageReadState getMessageReadState() {
		return messageReadState;
	}

	public void setMessageReadState(MessageReadState messageReadState) {
		this.messageReadState = messageReadState;
	}

	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}


}
