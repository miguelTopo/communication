package books.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import books.Book;
import books.BookRepository;
import books.BookRepositoryImpl;

@RestController
public class CustomerController {

	@Autowired
	private BookRepositoryImpl service;

	@RequestMapping(method = RequestMethod.POST, path = "/book")
	public ResponseEntity<Book> insert(@RequestBody Book book) {
		return service.save(book).map(v -> ResponseEntity.ok().body(v)).orElse(ResponseEntity.noContent().build());
	}

	@RequestMapping(method = RequestMethod.POST, path = "/list")
	public ResponseEntity<Book> list() {
		return service.save(new Book()).map(v -> ResponseEntity.ok().body(v))
				.orElse(ResponseEntity.noContent().build());
	}

}
