package co.edu.udistrital.user.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.structure.enums.State;
import co.edu.udistrital.structure.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {

	Optional<User> findByMobilePhoneAndState(String mobilePhone, State state);

	Optional<User> findByMobilePhoneOrEmailAndState(String mobilePhone, String email, State state);

}
