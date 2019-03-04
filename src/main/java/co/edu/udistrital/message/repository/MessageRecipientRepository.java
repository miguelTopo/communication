package co.edu.udistrital.message.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.message.model.MessageRecipient;

@Repository
public interface MessageRecipientRepository extends MongoRepository<MessageRecipient, String>, MessageRecipientRepositoryCustom {

	@Nullable
	@Async
	Optional<MessageRecipient> findByRecipientUserId(String recipientUserId);
}
