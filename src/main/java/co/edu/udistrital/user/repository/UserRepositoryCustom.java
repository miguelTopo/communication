package co.edu.udistrital.user.repository;

import java.util.List;

import co.edu.udistrital.structure.model.User;

public interface UserRepositoryCustom {

	public List<User> contactList(String userId);

}
