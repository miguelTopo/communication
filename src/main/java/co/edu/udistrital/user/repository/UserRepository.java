package co.edu.udistrital.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.Nullable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.structure.enums.State;
import co.edu.udistrital.structure.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom {

	@Nullable
	@Async
	Optional<User> findByMobilePhoneAndState(String mobilePhone, State state);

	@Nullable
	@Async
	Optional<User> findByMobilePhoneOrEmailAndState(String mobilePhone, String email, State state);
	
	List<User> findByMobilePhone(List<String> mobileList);

}
