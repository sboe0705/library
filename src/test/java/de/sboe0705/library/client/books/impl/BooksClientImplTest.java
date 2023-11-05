package de.sboe0705.library.client.books.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.sboe0705.library.client.books.BooksClient;
import de.sboe0705.library.model.Book;

@RestClientTest(BooksClient.class)
public class BooksClientImplTest {

	@Autowired
	private BooksClient underTest;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockRestServiceServer mockRestServiceServer;

	@Test
	void testGetBooks() throws Exception {
		int booksCount = 3;

		// given
		serverReturningBooks(booksCount);

		// when
		List<Book> books = underTest.getBooks();

		// then
		Assertions.assertThat(books) //
				.hasSize(booksCount) //
				.extracting(Book::getId, Book::getTitle, Book::getAuthor) //
				.containsExactly( //
						Tuple.tuple(1L, "Title 1", "Author 1"), //
						Tuple.tuple(2L, "Title 2", "Author 2"), //
						Tuple.tuple(3L, "Title 3", "Author 3") //
				);
	}

	private void serverReturningBooks(long expectedBooks) throws JsonProcessingException {
		List<Book> books = LongStream.rangeClosed(1, expectedBooks).mapToObj(id -> {
			Book book = new Book();
			book.setId(id);
			book.setTitle("Title " + id);
			book.setAuthor("Author " + id);
			return book;
		}).collect(Collectors.toList());
		String jsonString = objectMapper.writeValueAsString(books);
		mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(Matchers.endsWith("/books")))
				.andRespond(MockRestResponseCreators.withSuccess(jsonString, MediaType.APPLICATION_JSON));
	}

	@Test
	void testGetNotExistingsBooks() throws Exception {
		// given
		String jsonString = "[]";
		mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(Matchers.endsWith("/books")))
				.andRespond(MockRestResponseCreators.withSuccess(jsonString, MediaType.APPLICATION_JSON));

		// when
		List<Book> books = underTest.getBooks();

		// then
		Assertions.assertThat(books) //
				.isEmpty();
	}

	@Test
	void testGetBook() throws Exception {
		Long bookId = 1L;

		// given
		serverReturningBookWithId(bookId);

		// when
		Book book = underTest.getBook(1L);

		// then
		Assertions.assertThat(book) //
				.isNotNull() //
				.extracting(Book::getId, Book::getTitle, Book::getAuthor) //
				.containsExactly(1L, "Title 1", "Author 1");
	}

	@Test
	void testGetBookNotExisting() throws Exception {
		// given
		mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(Matchers.containsString("/book/")))
				.andRespond(MockRestResponseCreators.withStatus(HttpStatus.NOT_FOUND));

		// when
		Book book = underTest.getBook(1L);

		// then
		Assertions.assertThat(book) //
				.isNull();
	}

	private void serverReturningBookWithId(Long id) throws JsonProcessingException {
		Book book = new Book();
		book.setId(id);
		book.setTitle("Title " + id);
		book.setAuthor("Author " + id);
		String jsonString = objectMapper.writeValueAsString(book);
		mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(Matchers.containsString("/book/")))
				.andRespond(MockRestResponseCreators.withSuccess(jsonString, MediaType.APPLICATION_JSON));
	}

}
