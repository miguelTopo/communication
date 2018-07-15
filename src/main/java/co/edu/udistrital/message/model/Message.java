package co.edu.udistrital.message.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.structure.model.User;

@Document(collection = "Message")
public class Message {

	@Id private String id;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public User getUserCreator() {
		return userCreator;
	}

	public void setUserCreator(User userCreator) {
		this.userCreator = userCreator;
	}

	public String getUserCreatorId() {
		return userCreatorId;
	}

	public void setUserCreatorId(String userCreatorId) {
		this.userCreatorId = userCreatorId;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getParentMessageId() {
		return parentMessageId;
	}

	public void setParentMessageId(String parentMessageId) {
		this.parentMessageId = parentMessageId;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public boolean isReminder() {
		return isReminder;
	}

	public void setReminder(boolean isReminder) {
		this.isReminder = isReminder;
	}

	public Date getNextReminderDate() {
		return nextReminderDate;
	}

	public void setNextReminderDate(Date nextReminderDate) {
		this.nextReminderDate = nextReminderDate;
	}

	public ReminderFrequency getReminderFrequency() {
		return reminderFrequency;
	}

	public void setReminderFrequency(ReminderFrequency reminderFrequency) {
		this.reminderFrequency = reminderFrequency;
	}

	public String getReminderFrequencyId() {
		return reminderFrequencyId;
	}

	public void setReminderFrequencyId(String reminderFrequencyId) {
		this.reminderFrequencyId = reminderFrequencyId;
	}


}
