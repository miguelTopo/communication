package co.edu.udistrital.contact.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.rest.contact.model.UserContactRest;
import co.edu.udistrital.structure.model.User;

@Document(collection = "UserContact")
public class UserContact {

	/** Id único de la base de datis */
	@Id @Indexed private String id;

	/**
	 * Permite almacenar la infoemación del objeto User de la persona dueña de la libreta de
	 * direcciones
	 */
	@Transient private User user;

	/** Almacena el id del usuario dueño de la libreta de direcciones */
	@Indexed private String userId;

	/**
	 * Almacena el id del usuario que figura como contacto de la persona dueña de la libreta de
	 * direcciones
	 */
	@Indexed private String userContactId;

	/** Permite almacenar la información del último mensaje enviado a un contacto */
	@Transient private String lastMessage;

	/**
	 * Permite almacenar la información del la fecha-hora cuando ocurrió el mensaje enviado a un
	 * contacto
	 */
	@Transient private String lastMessageHour;

	/**
	 * Especifica el nombre personalizado qe tiene un contacto para el dueño de la libreta de
	 * direcciones
	 */
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

	public String getUserContactId() {
		return userContactId;
	}

	public void setUserContactId(String userContactId) {
		this.userContactId = userContactId;
	}

	public UserContactRest getUserContactRest() {
		UserContactRest ucr = new UserContactRest();
		ucr.setId(this.id);
		ucr.setCustomName(this.customName);
		ucr.setUser(this.user);
		ucr.setUserId(this.userId);
		ucr.setUserContactId(this.userContactId);
		ucr.setLastMessage(this.lastMessage);
		ucr.setLastMessageHour(this.lastMessageHour);
		return ucr;
	}
}
