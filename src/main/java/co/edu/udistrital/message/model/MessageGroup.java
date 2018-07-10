package co.edu.udistrital.message.model;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.structure.enums.DataState;
import co.edu.udistrital.structure.model.User;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "MessageGroup")
public class MessageGroup {

	@Id
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String name;

	@Getter
	@Setter
	private Date creationDate;

	@Getter
	@Setter
	private DataState dataState;

	@Getter
	@Setter
	private List<String> adminIdList;

	@Getter
	@Setter
	private List<User> adminList;

}
