package co.edu.udistrital.contact.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.rest.contact.model.UserContactRest;
import co.edu.udistrital.structure.model.User;

@Document(collection = "UserContact")
public class UserContact {

	@Id @Indexed private String id;

	@Transient private User user;

	@Indexed private String userId;

	@Transient private String lastMessage;

	@Transient private String lastMessageHour;

	private String customName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getCustomName() {
		return customName;
	}

	public void setCustomName(String customName) {
		this.customName = customName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getLastMessage() {
		return lastMessage;
	}

	public void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	public String getLastMessageHour() {
		return lastMessageHour;
	}

	public void setLastMessageHour(String lastMessageHour) {
		this.lastMessageHour = lastMessageHour;
	}

	public UserContactRest getUserContactRest() {
		UserContactRest ucr = new UserContactRest();
		ucr.setId(this.id);
		ucr.setCustomName(this.customName);
		ucr.setUser(this.user);
		ucr.setUserId(this.userId);
		ucr.setLastMessage(this.lastMessage);
		ucr.setLastMessageHour(this.lastMessageHour);
		return ucr;
	}

}
