package de.sboe0705.library.client.books;

import java.util.List;

import de.sboe0705.library.client.ClientErrorException;
import de.sboe0705.library.model.Book;

public interface BooksClient {

	String CLIENT_NAME = "books";

	List<Book> getBooks() throws ClientErrorException;

	Book getBook(Long id) throws ClientErrorException;

}
