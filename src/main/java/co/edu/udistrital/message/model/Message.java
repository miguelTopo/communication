package co.edu.udistrital.message.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.web.multipart.MultipartFile;

import co.edu.udistrital.common.model.CoreEntity;
import co.edu.udistrital.message.enums.MessageType;
import co.edu.udistrital.structure.model.User;


@Entity
@Table(name = "Message")
public class Message extends CoreEntity {

	public Message() {}

	@Transient private MultipartFile multipartFile;

	@Column(name = "senderUserId", nullable = false) private String senderUserId;

	@Column(name = "messageBody") private String messageBody;

	@Column(name = "file") private String file;

	@Column(name = "parentMessageId") private String parentMessageId;

	@Transient private User senderUser;

	@Transient private User receiverUser;

	@Column(name = "messageType", nullable = false) private MessageType messageType;

	@OneToMany(mappedBy = "message") private List<MessageRecipient> messageRecipientList;

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getParentMessageId() {
		return parentMessageId;
	}

	public void setParentMessageId(String parentMessageId) {
		this.parentMessageId = parentMessageId;
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

	public List<MessageRecipient> getMessageRecipientList() {
		return messageRecipientList;
	}

	public void setMessageRecipientList(List<MessageRecipient> messageRecipientList) {
		this.messageRecipientList = messageRecipientList;
	}



}
