package co.edu.udistrital.message.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.structure.model.User;
import lombok.Data;

@Data
@Document(collection = "Message")
public class Message {

	@Id
	private String id;

	private String subject;

	private User userCreator;

	private String userCreatorId;

	private String messageBody;

	private Date creationDate;

	private String parentMessageId;

	private Date expirationDate;

	private boolean isReminder;

	private Date nextReminderDate;

	private ReminderFrequency reminderFrequency;

	private String reminderFrequencyId;
}
