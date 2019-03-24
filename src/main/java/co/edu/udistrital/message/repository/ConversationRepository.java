package co.edu.udistrital.message.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.message.model.Conversation;

@Repository
public interface ConversationRepository extends MongoRepository<Conversation, String>, ConversationRepositoryCustom {

}
