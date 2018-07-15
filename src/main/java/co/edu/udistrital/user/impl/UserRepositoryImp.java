package co.edu.udistrital.user.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import co.edu.udistrital.structure.model.User;
import co.edu.udistrital.user.repository.UserRepositoryCustom;

public class UserRepositoryImp implements UserRepositoryCustom {

	@Override
	public List<User> contactList(String userId) {
		final Query query = new Query();
		final List<Criteria> criteria = new ArrayList<>();
		
//		criteria.add(Criteria.where(""). )
		
		
		
		return null;
	}



}
