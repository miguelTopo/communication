package co.edu.udistrital.user.repository;

import java.util.List;

import co.edu.udistrital.structure.model.User;

public interface UserRepositoryCustom {

	List<User> loadByMobilePhoneActiveState(List<String> mobilePhones);

	List<User> loadByMobilePhoneInactiveState(List<String> mobilePhones);

	List<User> loadByMobilePhoneIn(List<String> mobilePhones);

	List<User> findByIdIn(List<String> idList);

}
