package co.edu.udistrital.contact.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.structure.model.User;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "UserContact")
public class UserContact {

	@Id
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String userId;

	@Getter
	@Setter
	private String userContactId;

	@Getter
	@Setter
	private User user;

	@Getter
	@Setter
	private User userContact;

}
