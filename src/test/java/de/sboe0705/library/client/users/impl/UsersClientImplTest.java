package de.sboe0705.library.client.users.impl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.assertj.core.api.Assertions;
import org.assertj.core.groups.Tuple;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.sboe0705.library.client.users.UsersClient;
import de.sboe0705.library.configuration.LibraryConfiguration;
import de.sboe0705.library.configuration.RestClientConfiguration;
import de.sboe0705.library.model.Book;
import de.sboe0705.library.model.User;

@RestClientTest(UsersClient.class)
public class UsersClientImplTest {

	@TestConfiguration
	public static class WebClientConfiguration {

		@Bean
		public LibraryConfiguration getLibraryConfiguration() {
			LibraryConfiguration libraryConfiguration = new LibraryConfiguration();
			RestClientConfiguration restClientConfiguration = new RestClientConfiguration();
			restClientConfiguration.setName(UsersClient.CLIENT_NAME);
			restClientConfiguration.setHost("localhost");
			restClientConfiguration.setPort("8080");
			libraryConfiguration.setRestClients(List.of(restClientConfiguration));
			return libraryConfiguration;
		}

	}

	@Autowired
	private UsersClient underTest;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockRestServiceServer mockRestServiceServer;

	@Test
	void testGetUsers() throws Exception {
		int usersCount = 3;

		// given
		serverReturningUsers(usersCount);

		// when
		List<User> users = underTest.getUsers();

		// then
		Assertions.assertThat(users) //
				.hasSize(usersCount) //
				.extracting(User::getId, User::getFirstName, User::getLastName) //
				.containsExactly( //
						Tuple.tuple("user1", "First1", "Last1"), //
						Tuple.tuple("user2", "First2", "Last2"), //
						Tuple.tuple("user3", "First3", "Last3") //
				);
	}

	private void serverReturningUsers(long expectedUsers) throws JsonProcessingException {
		List<User> users = LongStream.rangeClosed(1, expectedUsers).mapToObj(id -> {
			User user = new User();
			user.setId("user" + id);
			user.setFirstName("First" + id);
			user.setLastName("Last" + id);
			return user;
		}).collect(Collectors.toList());
		String jsonString = objectMapper.writeValueAsString(users);
		mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(Matchers.endsWith("/users")))
				.andRespond(MockRestResponseCreators.withSuccess(jsonString, MediaType.APPLICATION_JSON));
	}

	@Test
	void testGetUser() throws Exception {
		// given
		serverReturningUserWithId(1L);

		// when
		User user = underTest.getUser("user1");

		// then
		Assertions.assertThat(user) //
				.isNotNull() //
				.extracting(User::getId, User::getFirstName, User::getLastName) //
				.containsExactly("user1", "First1", "Last1");
	}

	private void serverReturningUserWithId(Long id) throws JsonProcessingException {
		User user = new User();
		user.setId("user" + id);
		user.setFirstName("First" + id);
		user.setLastName("Last" + id);
		String jsonString = objectMapper.writeValueAsString(user);
		mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(Matchers.containsString("/user/")))
				.andRespond(MockRestResponseCreators.withSuccess(jsonString, MediaType.APPLICATION_JSON));
	}
	
	@Test
	void testGetBookNotExisting() throws Exception {
		// given
		mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(Matchers.containsString("/user/")))
		.andRespond(MockRestResponseCreators.withStatus(HttpStatus.NOT_FOUND));
		
		// when
		User user = underTest.getUser("user1");
		
		// then
		Assertions.assertThat(user) //
		.isNull();
	}

}
