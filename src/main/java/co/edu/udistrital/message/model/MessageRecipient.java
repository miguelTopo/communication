package co.edu.udistrital.message.model;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import co.edu.udistrital.common.model.CoreEntity;
import co.edu.udistrital.message.enums.MessageReadState;
import co.edu.udistrital.structure.model.User;

@Entity
@Table(name = "MessageRecipient")
public class MessageRecipient extends CoreEntity {

	public MessageRecipient() {
		// Basic Empty Constructor
	}

	@Column(name = "recipientUserId") private String recipientUserId;

	@Column(name = "recipientMessageGroupId") private String recipientMessageGroupId;

	@Column(name = "messageId", nullable = false, insertable = false, updatable = false) private Long messageId;

	@Column(name = "readDate") private Calendar readDate;

	@ManyToOne @JoinColumn(name = "messageId") private Message message;

	@Transient private User recipientUser;

	@Transient private MessageGroup recipientMessageGroup;

	@Column(name = "messageReadState", nullable = false) private MessageReadState messageReadState;


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

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
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

	public Calendar getReadDate() {
		return readDate;
	}

	public void setReadDate(Calendar readDate) {
		this.readDate = readDate;
	}


}
