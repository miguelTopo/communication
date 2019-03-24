package co.edu.udistrital.message.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public class MessageRepositoryImpl {

	@Autowired private MongoTemplate mongoTemplate;

	public MessageRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

}
