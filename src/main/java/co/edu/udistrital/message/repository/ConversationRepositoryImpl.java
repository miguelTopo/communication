package co.edu.udistrital.message.repository;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.BasicDBObject;

import co.edu.udistrital.message.model.Conversation;
import co.edu.udistrital.structure.api.AttrName;

public class ConversationRepositoryImpl implements ConversationRepositoryCustom {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public ConversationRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public Conversation findByBasicConversation(List<String> userIdList) {
		Query query = new Query(Criteria.where(AttrName.USER_ID_LIST).in(userIdList));
		return mongoTemplate.findOne(query, Conversation.class);
	}

	@Override
	public List<Conversation> findByHomeUserId(String homeUserId) {
		Query query = new Query(Criteria.where(AttrName.USER_ID_LIST).in(Arrays.asList(homeUserId)));
		return mongoTemplate.find(query, Conversation.class);
	}

	@Override
	public List<Conversation> findLastMessageList(String userId) {
		Query query = new Query(Criteria.where(AttrName.USER_ID_LIST).all(Arrays.asList(userId)));
		return mongoTemplate.find(query, Conversation.class);
	}

}
