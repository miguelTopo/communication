package co.edu.udistrital.message.repository;

import java.util.List;

import co.edu.udistrital.message.model.Conversation;

public interface ConversationRepositoryCustom {

	Conversation findByBasicConversation(List<String> userIdList);

}
