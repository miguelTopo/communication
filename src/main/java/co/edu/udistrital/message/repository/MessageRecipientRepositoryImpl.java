package co.edu.udistrital.message.repository;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GraphLookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import co.edu.udistrital.message.model.MessageRecipient;

public class MessageRecipientRepositoryImpl implements MessageRecipientRepositoryCustom {

	@Autowired private MongoTemplate mongoTemplate;

	public MessageRecipientRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<MessageRecipient> loadMessageTreeTalk(String messageId) {
		GraphLookupOperation graphLookupOperation = GraphLookupOperation.builder().from("MessageRecipient").startWith(messageId)
			.connectFrom("messageId").connectTo("messageId").as("reportingHierarchy");
		Document document = graphLookupOperation.toDocument(Aggregation.DEFAULT_CONTEXT);
		System.out.println(document == null);
		// Query query = new
		// Query(Criteria.where(AttrName.MOBILE_PHONE).is("").and(AttrName.STATE).is(""));
		Query query = new Query(Criteria.where("").is(""));
		return this.mongoTemplate.find(query, MessageRecipient.class);
	}


}
