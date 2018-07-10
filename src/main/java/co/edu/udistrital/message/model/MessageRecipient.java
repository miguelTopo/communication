package co.edu.udistrital.message.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.structure.model.User;
import lombok.Getter;
import lombok.Setter;

@Document(collection = "MessageRecipient")
public class MessageRecipient {

	@Id
	@Getter
	@Setter
	private String id;

	@Getter
	@Setter
	private String recipientId;

	@Getter
	@Setter
	private User recipientUser;

	@Getter
	@Setter
	private String recipientMessageGroupId;

	@Getter
	@Setter
	private MessageGroup recipientMessageGroup;

	@Getter
	@Setter
	private String messageId;

	@Getter
	@Setter
	private Message message;

	@Getter
	@Setter
	private boolean isRead;
}
