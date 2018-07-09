//package books;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.ZoneId;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Import;
//import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { BookRepository.class })
//@EnableMongoRepositories
////@Import(EmbeddedMongoConfiguration.class)
//public class BookRepositoryTest {
//
//	@Autowired
//	private BookRepository bookRepository;
//
//	@Test
//	public void findByTitleContainingOrderByTitle_existingTitle_shouldReturnList() {
//		// Given
//		// DB with default books
//		final String existingBookPartialTitle = "Cien";
//
//		// When
//		final List<Book> books = bookRepository.findByTitleContainingOrderByTitle(existingBookPartialTitle);
//	
//		// Then
//		final int expectedCount = 1;
//		Assert.assertEquals(expectedCount, books.size());
//		Assert.assertEquals(books.size(),
//				books.stream().filter(b -> b.getTitle().contains(existingBookPartialTitle)).count());
//	}
//	
//	
//	@Test
//	public void query_combinedQuery_shouldReturnList() {
//		// Given
//		// DB with default books
//		final String authorName = "Laakmann McDow";
//		final Date dateAfter = Date.from(LocalDate.of(2011, 8, 22)
//				.atStartOfDay().atZone(ZoneId.of("GMT_ZONE_ID")).toInstant());
//		final Date dateBefore = Date.from(LocalDate.of(2011, 8, 22)
//				.atTime(LocalTime.MAX).atZone(ZoneId.of("GMT_ZONE_ID")).toInstant());
//		final String subject = "JOB HUNTING";
//		final DynamicQuery dynamicQuery = new DynamicQuery();
//		dynamicQuery.setAuthorNameLike(authorName);
//		dynamicQuery.setPublishDateAfter(dateAfter);
//		dynamicQuery.setPublishDateBefore(dateBefore);
//		dynamicQuery.setSubject(subject);
//
//		// When
//		final List<Book> books = bookRepository.query(dynamicQuery);
//
//		// Then
//		final int expectedCount = 1;
//		Assert.assertEquals(expectedCount, books.size());
//	}
//
//
//}
