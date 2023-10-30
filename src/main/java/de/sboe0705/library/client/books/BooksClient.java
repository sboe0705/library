package de.sboe0705.library.client.books;

import java.util.List;

import de.sboe0705.library.model.Book;

public interface BooksClient {

	List<Book> getBooks();
	
	Book getBook(Long id);
	
}
