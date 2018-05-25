package co.edu.udistrital.message.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import co.edu.udistrital.message.model.MessageGroup;

public interface MessageGroupRepository extends MongoRepository<MessageGroup, String> {

}
