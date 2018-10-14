package co.edu.udistrital.contact.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import co.edu.udistrital.contact.model.UserContact;
import co.edu.udistrital.contact.repository.UserContactRepositoryCustom;
import co.edu.udistrital.structure.api.AttrName;

public class UserContactRepositoryImpl implements UserContactRepositoryCustom {

	@Autowired private MongoTemplate mongoTemplate;

	public UserContactRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	public List<UserContact> findByIdIn(List<String> idList) {
		Query query = new Query(Criteria.where(AttrName.ID).in(idList));
		return mongoTemplate.find(query, UserContact.class);
	}


}
