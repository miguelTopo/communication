  package co.edu.udistrital.message.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.structure.model.User;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Document(collection = "Message")
public class Message {

	@Id
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String subject;

	@Getter
	@Setter
	private User userCreator;

	@Getter
	@Setter
	private String userCreatorId;

	@Getter
	@Setter
	private String messageBody;

	@Getter
	@Setter
	private Date creationDate;

	@Getter
	@Setter
	private String parentMessageId;

	@Getter
	@Setter
	private Date expirationDate;

	@Getter
	@Setter
	private boolean isReminder;

	@Getter
	@Setter
	private Date nextReminderDate;

	@Getter
	@Setter
	private ReminderFrequency reminderFrequency;

	@Getter
	@Setter
	private String reminderFrequencyId;
}
