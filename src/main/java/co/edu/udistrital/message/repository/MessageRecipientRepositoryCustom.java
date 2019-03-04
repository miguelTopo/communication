package co.edu.udistrital.message.repository;

import java.util.List;

import co.edu.udistrital.message.model.MessageRecipient;

public interface MessageRecipientRepositoryCustom {

	List<MessageRecipient> loadMessageTreeTalk(String messageId);

}
