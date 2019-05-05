package co.edu.udistrital.rest.contact.model;

import co.edu.udistrital.structure.model.User;

public class UserContactRest {

	private String id;

	private User user;

	private User userContact;

	private String userId;

	private String userContactId;

	private String customName;

	private String lastMessageHour;

	private String lastMessage;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUserContact() {
		return userContact;
	}

	public void setUserContact(User userContact) {
		this.userContact = userContact;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getLastMessageHour() {
		return lastMessageHour;
	}

	public void setLastMessageHour(String lastMessageHour) {
		this.lastMessageHour = lastMessageHour;
	}

	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public String getUserContactId() {
		return userContactId;
	}

	public void setUserContactId(String userContactId) {
		this.userContactId = userContactId;
	}
}
