package co.edu.udistrital.rest.message.model;

import java.io.Serializable;

import co.edu.udistrital.message.enums.MessageType;
import co.edu.udistrital.structure.enums.State;

public class MessageRest implements Serializable {

	private State state;

	private String senderUserId;

	private String receiverUserId;

	private String messageBody;

	private String file;

	private MessageType messageType;


	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
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

	public String getReceiverUserId() {
		return receiverUserId;
	}

	public void setReceiverUserId(String receiverUserId) {
		this.receiverUserId = receiverUserId;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public MessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

}
