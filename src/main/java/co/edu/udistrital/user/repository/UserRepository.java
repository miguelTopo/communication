package co.edu.udistrital.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.structure.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {
	

}
