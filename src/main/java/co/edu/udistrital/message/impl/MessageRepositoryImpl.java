package co.edu.udistrital.message.impl;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

import java.util.Arrays;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.match;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.graphLookup;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GraphLookupOperation;
import org.springframework.data.mongodb.core.query.Criteria;

import com.mongodb.operation.AggregateOperation;

import co.edu.udistrital.message.repository.MessageRepositoryCustom;

public class MessageRepositoryImpl implements MessageRepositoryCustom {

	@Autowired private MongoTemplate mongoTemplate;

	public MessageRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}


	public void getConversation(String userId, String userFriendId) {
		userId = "";
		userFriendId = "";
		//@formatter:off
		GraphLookupOperation operation =
			GraphLookupOperation.builder()
			.from("Message")
			.startWith("")
			.connectFrom("")
			.connectTo("")
			.depthField("")
			.maxDepth(20)
			.as("");
		//@formatter:on

		// this.mongoTemplate.aggregate(aggregation, outputType)
	}


}
