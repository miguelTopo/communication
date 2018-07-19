package co.edu.udistrital.user.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import books.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, ObjectId> {

}
