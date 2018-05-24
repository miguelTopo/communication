package books;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {

	List<Book> findByTitleContainingOrderByTitle(String titleContains);

}
