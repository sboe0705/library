package de.sboe0705.library.client.books.impl;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import de.sboe0705.library.client.ClientErrorException;
import de.sboe0705.library.client.books.BooksClient;
import de.sboe0705.library.model.Book;

@Service
public class BooksClientImpl implements BooksClient {

	@Value("${library.books-rest-client.protocol:http}://${library.books-rest-client.host}:${library.books-rest-client.port}")
	private URI baseURI;

	private RestTemplate restTemplate;

	public BooksClientImpl(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.build();
	}

	@Override
	public List<Book> getBooks() throws ClientErrorException {
		try {
			URI booksURI = URI.create("/books");
			ResponseEntity<List<Book>> response = restTemplate.exchange(baseURI.resolve(booksURI), HttpMethod.GET, null,
					new ParameterizedTypeReference<List<Book>>() {
					});
			return response.getBody();
		} catch (HttpClientErrorException e) {
			throw new ClientErrorException(BooksClient.CLIENT_NAME, e);
		}
	}

	@Override
	public Book getBook(Long id) throws ClientErrorException {
		try {
			URI bookURI = URI.create("/book/" + id);
			try {
				ResponseEntity<Book> response = restTemplate.exchange(baseURI.resolve(bookURI), HttpMethod.GET, null,
						Book.class);
				return response.getBody();
			} catch (HttpClientErrorException.NotFound notFound) {
				return null;
			}
		} catch (HttpClientErrorException e) {
			throw new ClientErrorException(BooksClient.CLIENT_NAME, e);
		}
	}

}
