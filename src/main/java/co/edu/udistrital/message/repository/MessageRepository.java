package co.edu.udistrital.message.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.message.model.Message;

@Repository
public interface MessageRepository extends MongoRepository<Message, String>, MessageRepositoryCustom {

}
