package de.sboe0705.library.rest;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import de.sboe0705.library.client.ClientErrorException;
import de.sboe0705.library.client.books.BooksClient;
import de.sboe0705.library.client.rentals.RentalsClient;
import de.sboe0705.library.model.Book;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class LibraryController {

	@Autowired
	private BooksClient booksClient;

	@Autowired
	private RentalsClient rentalsClient;

	@Operation(summary = "Get all available books")
	@GetMapping("/available-books")
	public List<Book> getAvailableBooks() {
		try {
			List<Book> books = booksClient.getBooks();

			List<Long> bookIds = books.stream() //
					.map(Book::getId) //
					.collect(Collectors.toList());
			Map<Long, Boolean> rentInfoMappedByBookId = rentalsClient.areItemsRent(bookIds);

			List<Book> availableBooks = books.stream() //
					.filter(book -> {
						Boolean isBookRent = Optional.ofNullable(rentInfoMappedByBookId.get(book.getId())) //
								.orElse(false);
						return !isBookRent;
					}) //
					.collect(Collectors.toList());

			return availableBooks;
		} catch (ClientErrorException e) {
			throw new IllegalStateException("Something went wrong!", e);
		}
	}

}
