package co.edu.udistrital.contact.repository;

import java.util.List;

import co.edu.udistrital.contact.model.UserContact;

public interface UserContactRepositoryCustom {

	List<UserContact> findByIdIn(List<String> idList);

}
