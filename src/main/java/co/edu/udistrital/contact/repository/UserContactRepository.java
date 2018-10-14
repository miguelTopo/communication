package co.edu.udistrital.contact.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.contact.model.UserContact;

@Repository
public interface UserContactRepository extends MongoRepository<UserContact, String>, UserContactRepositoryCustom {

}
