package de.sboe0705.library.client.rentals.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ListAssert;
import org.assertj.core.groups.Tuple;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.client.match.MockRestRequestMatchers;
import org.springframework.test.web.client.response.MockRestResponseCreators;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.sboe0705.library.client.ClientErrorException;
import de.sboe0705.library.client.rentals.RentalsClient;
import de.sboe0705.library.client.rentals.gen.api.RentalsControllerApi;
import de.sboe0705.library.client.rentals.gen.invoker.ApiClient;
import de.sboe0705.library.client.rentals.gen.model.Rent;
import de.sboe0705.library.configuration.LibraryConfiguration;
import de.sboe0705.library.configuration.RestClientConfiguration;

@RestClientTest(components = { RentalsApiClientConfiguration.class, ApiClient.class, RentalsControllerApi.class,
		RentalsClient.class })
public class RentalsClientImplTest {

	@TestConfiguration
	public static class WebClientConfiguration {

		@Bean
		public LibraryConfiguration getLibraryConfiguration() {
			LibraryConfiguration libraryConfiguration = new LibraryConfiguration();
			RestClientConfiguration restClientConfiguration = new RestClientConfiguration();
			restClientConfiguration.setName(RentalsClient.CLIENT_NAME);
			restClientConfiguration.setHost("localhost");
			restClientConfiguration.setPort("8080");
			libraryConfiguration.setRestClients(List.of(restClientConfiguration));
			return libraryConfiguration;
		}

	}

	@Autowired
	private RentalsClient underTest;

	@Autowired
	private MockRestServiceServer mockRestServiceServer;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void testGetRentsOnlyRent() throws Exception {
		testGetRentsWithParameter(true);
		testGetRentsWithParameter(false);
	}

	private void testGetRentsWithParameter(boolean onlyRent) throws Exception {
		int rentsCount = 3;

		// given
		serverReturningRents(rentsCount, onlyRent);

		// when
		List<Rent> rents = underTest.getRents(onlyRent);

		// then
		Assertions.assertThat(rents) //
				.hasSize(rentsCount) //
				.extracting(Rent::getId, Rent::getItemId, Rent::getUserId, Rent::getRent) //
				.containsExactly( //
						Tuple.tuple(1L, 1L, "user1", onlyRent), //
						Tuple.tuple(2L, 2L, "user2", onlyRent), //
						Tuple.tuple(3L, 3L, "user3", onlyRent) //
				);

		ListAssert<Rent> rentsAssert = Assertions.assertThat(rents) //
				.allMatch(rent -> rent.getRentSince() != null);

		if (onlyRent) {
			rentsAssert.allMatch(rent -> rent.getReturnedAt() == null);
		} else {
			rentsAssert.allMatch(rent -> rent.getReturnedAt() != null);
		}

		mockRestServiceServer.reset();
	}

	private void serverReturningRents(long expectedRents, boolean onlyRent) throws JsonProcessingException {
		List<Rent> rents = LongStream.rangeClosed(1, expectedRents).mapToObj(id -> {
			Rent rent = new Rent();
			rent.setId(id);
			rent.setItemId(id);
			rent.setUserId("user" + id);
			rent.setRentSince(LocalDateTime.of(2023, 11, (int) id, 0, 0));
			rent.setRent(onlyRent);
			if (!onlyRent) {
				rent.setReturnedAt(LocalDateTime.of(2023, 12, (int) id, 0, 0));
			}
			return rent;
		}).collect(Collectors.toList());
		String jsonString = objectMapper.writeValueAsString(rents);
		mockRestServiceServer
				.expect(MockRestRequestMatchers.requestTo(Matchers.endsWith("/rents?onlyRent=" + onlyRent)))
				.andRespond(MockRestResponseCreators.withSuccess(jsonString, MediaType.APPLICATION_JSON));
	}

	@Test
	void testIsRent() throws Exception {
		// given
		mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(Matchers.endsWith("/is-rent/item/7")))
				.andRespond(MockRestResponseCreators.withSuccess("true", MediaType.APPLICATION_JSON));
		mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(Matchers.endsWith("/is-rent/item/5")))
				.andRespond(MockRestResponseCreators.withSuccess("false", MediaType.APPLICATION_JSON));
		mockRestServiceServer.expect(MockRestRequestMatchers.requestTo(Matchers.endsWith("/is-rent/item/3")))
				.andRespond(MockRestResponseCreators.withBadRequest());

		// when ... then
		Assertions.assertThat(underTest.isItemRent(7L)).isTrue();
		Assertions.assertThat(underTest.isItemRent(5L)).isFalse();
		Assertions.assertThatThrownBy(() -> underTest.isItemRent(3L)) //
				.isInstanceOf(ClientErrorException.class) //
				.hasMessage("Unexpected error from client 'rentals'");
	}

	@Test
	void testAreItemsRent() throws Exception {
		// given
		mockRestServiceServer
				.expect(MockRestRequestMatchers
						.requestTo(Matchers.containsStringIgnoringCase("/is-rent/items?itemIds=3,5,7")))
				.andRespond(MockRestResponseCreators.withSuccess("{\"3\": false, \"5\": true, \"7\": false}",
						MediaType.APPLICATION_JSON));

		// when
		Map<Long, Boolean> items = underTest.areItemsRent(List.of(3L, 5L, 7L));

		// then
		Assertions.assertThat(items.entrySet()) //
				.extracting(Map.Entry::getKey, Entry::getValue).contains( //
						Tuple.tuple(3L, false), //
						Tuple.tuple(5L, true), //
						Tuple.tuple(7L, false) //
				);
	}

	@Test
	void testRentItem() throws Exception {
		// given
		mockRestServiceServer
				.expect(MockRestRequestMatchers.requestTo(Matchers.containsStringIgnoringCase("/rent/item/1/by/user1")))
				.andRespond(MockRestResponseCreators.withSuccess());

		// when
		underTest.rentItem(1L, "user1");

		// then
		mockRestServiceServer.verify();
	}

	@Test
	void testReturnItem() throws Exception {
		// given
		mockRestServiceServer
				.expect(MockRestRequestMatchers.requestTo(Matchers.containsStringIgnoringCase("/return/item/1")))
				.andRespond(MockRestResponseCreators.withSuccess());

		// when
		underTest.returnItem(1L);

		// then
		mockRestServiceServer.verify();
	}

}
