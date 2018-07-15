package co.edu.udistrital.contact.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.structure.enums.State;
import co.edu.udistrital.structure.model.User;

@Document(collection = "UserContact")
public class UserContact {

	@Id private String id;

	private String userId;

	private List<String> contactId;

	private User user;

	private User contact;

	private State state;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}


	public List<String> getContactId() {
		return contactId;
	}

	public void setContactId(List<String> contactId) {
		this.contactId = contactId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getContact() {
		return contact;
	}

	public void setContact(User contact) {
		this.contact = contact;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}
}
