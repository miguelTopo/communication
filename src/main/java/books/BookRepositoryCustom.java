package books;

import java.util.List;

public interface BookRepositoryCustom {

	List<Book> query(DynamicQuery dynamicQuery);
}
