package books;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.MongoRegexCreator;
import org.springframework.data.mongodb.core.query.MongoRegexCreator.MatchMode;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class BookRepositoryImpl implements BookRepositoryCustom {

	private final MongoTemplate mongoTemplate;

	@Autowired
	public BookRepositoryImpl(MongoTemplate mongoTemplate) {
		this.mongoTemplate = mongoTemplate;
	}

	@Override
	public List<Book> query(DynamicQuery dynamicQuery) {
		final Query query = new Query();
		final List<Criteria> criteria = new ArrayList<>();
		if (dynamicQuery.getAuthorNameLike() != null) {
			criteria.add(Criteria.where("authorNames").regex(MongoRegexCreator.INSTANCE
					.toRegularExpression(dynamicQuery.getAuthorNameLike(), MatchMode.CONTAINING), "i"));
		}

		if (dynamicQuery.getPublishDateBefore() != null) {
			criteria.add(Criteria.where("publishDate").lte(dynamicQuery.getPublishDateBefore()));
		}
		
		if (dynamicQuery.getPublishDateAfter() != null) {
			criteria.add(Criteria.where("publishDate").gte(dynamicQuery.getPublishDateBefore()));
		}

		// if(dynamicQuery.getSubject()!=null){
		// criteria.add(Criteria.where("subjects").regex(MongoRegexCreator.INSTANCE.toRegularExpression(dynamicQuery.getSubject(),
		// MatchMode.) ) )
		// }

		if (!criteria.isEmpty()) {
			criteria.add(new Criteria().andOperator(criteria.toArray(new Criteria[criteria.size()])));
		}

		return mongoTemplate.find(query, Book.class);
	}
	
	public Optional<Book>save(Book book){
		mongoTemplate.save(book);
		return Optional.of(book) ;
	}

}
