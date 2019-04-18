package co.edu.udistrital.structure.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.structure.model.UserLangPeferences;

@Repository
public interface UserLangPeferencesRepository extends MongoRepository<UserLangPeferences, String> {

}
