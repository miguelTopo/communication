package co.edu.udistrital.message.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import co.edu.udistrital.structure.model.User;
import lombok.Data;

@Data
@Document(collection = "MessageRecipient")
public class MessageRecipient {

	@Id
	private String id;

	private String recipientId;

	private User recipientUser;

	private String recipientMessageGroupId;

	private MessageGroup recipientMessageGroup;

	private String messageId;

	private Message message;

	private boolean isRead;
}
