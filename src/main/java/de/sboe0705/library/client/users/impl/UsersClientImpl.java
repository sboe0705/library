package de.sboe0705.library.client.users.impl;

import java.net.URI;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import de.sboe0705.library.client.users.UsersClient;
import de.sboe0705.library.configuration.LibraryConfiguration;
import de.sboe0705.library.configuration.RestClientConfiguration;
import de.sboe0705.library.model.User;

@Service
public class UsersClientImpl implements UsersClient {

	private RestClientConfiguration restClientConfiguration;

	private RestTemplate restTemplate;

	public UsersClientImpl(LibraryConfiguration libraryConfiguration, RestTemplateBuilder restTemplateBuilder) {
		restClientConfiguration = libraryConfiguration.getRestClientByName(UsersClient.CLIENT_NAME);
		restTemplate = restTemplateBuilder.build();
	}

	@Override
	public List<User> getUsers() {
		URI usersURI = URI.create("/users");
		ResponseEntity<List<User>> response = restTemplate.exchange(
				restClientConfiguration.getBaseURI().resolve(usersURI), HttpMethod.GET, null,
				new ParameterizedTypeReference<List<User>>() {
				});
		return response.getBody();
	}

	@Override
	public User getUser(String id) {
		URI userURI = URI.create("/user/" + id);
		try {
			ResponseEntity<User> response = restTemplate.exchange(restClientConfiguration.getBaseURI().resolve(userURI),
					HttpMethod.GET, null, User.class);
			return response.getBody();
		} catch (HttpClientErrorException.NotFound notFound) {
			return null;
		}
	}

}
