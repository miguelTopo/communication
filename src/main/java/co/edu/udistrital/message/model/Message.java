package co.edu.udistrital.message.model;

import java.util.Calendar;

import org.springframework.data.annotation.Transient;
import org.springframework.web.multipart.MultipartFile;

import co.edu.udistrital.core.util.DateUtil;
import co.edu.udistrital.message.enums.MessageType;
import co.edu.udistrital.structure.enums.State;
import co.edu.udistrital.structure.model.User;


public class Message {

	private Calendar creationDate;

	private Calendar updateDate;

	private String creationUserId;

	private String updateUserId;

	private State state;

	@Transient private MultipartFile multipartFile;

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



	public Calendar getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public Calendar getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Calendar updateDate) {
		this.updateDate = updateDate;
	}

	public String getCreationUserId() {
		return creationUserId;
	}

	public void setCreationUserId(String creationUserId) {
		this.creationUserId = creationUserId;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public void addAuditInfo(boolean isNew, String userId) {
		if (isNew) {
			this.creationDate = DateUtil.getCurrentCalendar();
			this.creationUserId = userId;
			this.state = State.ACTIVE;
		} else {
			this.updateDate = DateUtil.getCurrentCalendar();
			this.updateUserId = userId;
		}
	}

}
