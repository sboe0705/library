package de.sboe0705.library.rest;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import de.sboe0705.library.client.books.BooksClient;
import de.sboe0705.library.client.rentals.RentalsClient;
import de.sboe0705.library.model.Book;

@WebMvcTest
public class LibraryControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private BooksClient booksClientMock;

	@MockBean
	private RentalsClient rentalsClientMock;

	@AfterEach
	public void tearDown() {
		Mockito.verifyNoMoreInteractions(booksClientMock, rentalsClientMock);
	}

	@Test
	void testGetAvailableBooks() throws Exception {
		List<Book> allBooks = LongStream.rangeClosed(1, 4).mapToObj(id -> {
			Book book = new Book();
			book.setId(id);
			book.setTitle("Title " + id);
			book.setAuthor("Author " + id);
			return book;
		}).collect(Collectors.toList());

		List<Long> allBookIds = allBooks.stream().map(Book::getId).collect(Collectors.toList());

		Map<Long, Boolean> rentInfoByBookIds = Map.of(1L, true, 2L, false, 3L, false);

		// given
		Mockito.when(booksClientMock.getBooks()).thenReturn(allBooks);

		Mockito.when(rentalsClientMock.areItemsRent(allBookIds)).thenReturn(rentInfoByBookIds);

		// when
		ResultActions result = mockMvc.perform(MockMvcRequestBuilders.get("/available-books"));

		// then
		result.andDo(MockMvcResultHandlers.print()) //
				.andExpect(MockMvcResultMatchers.status().isOk()) //
				.andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(3))) //
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.equalTo(2))) //
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].title", Matchers.equalTo("Title 2"))) //
				.andExpect(MockMvcResultMatchers.jsonPath("$[0].author", Matchers.equalTo("Author 2"))) //
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.equalTo(3))) //
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].title", Matchers.equalTo("Title 3"))) //
				.andExpect(MockMvcResultMatchers.jsonPath("$[1].author", Matchers.equalTo("Author 3"))) //
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].id", Matchers.equalTo(4))) //
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].title", Matchers.equalTo("Title 4"))) //
				.andExpect(MockMvcResultMatchers.jsonPath("$[2].author", Matchers.equalTo("Author 4"))) //
		;

		Mockito.verify(booksClientMock).getBooks();
		Mockito.verify(rentalsClientMock).areItemsRent(allBookIds);
	}

}
