package de.sboe0705.library.configuration;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LibraryConfigurationTest {

	private static final String EXISTING_CLIENT = "rentals";

	private static final String NOT_EXISTING_CLIENT = "books";

	private LibraryConfiguration underTest;

	@BeforeEach
	void setUp() {
		underTest = new LibraryConfiguration();
		RestClientConfiguration restClientConfiguration = new RestClientConfiguration();
		restClientConfiguration.setName(EXISTING_CLIENT);
		restClientConfiguration.setHost("localhost");
		restClientConfiguration.setPort("8080");
		underTest.setRestClients(List.of(restClientConfiguration));
	}

	@Test
	void testGetRestClientByName() throws Exception {
		// when
		RestClientConfiguration restClientConfiguration = underTest.getRestClientByName(EXISTING_CLIENT);

		// then
		Assertions.assertThat(restClientConfiguration) //
				.isNotNull() //
				.hasFieldOrPropertyWithValue("name", EXISTING_CLIENT);
	}

	@Test
	void testGetRestClientByNameWithNotExistingClient() throws Exception {
		// when .. then
		Assertions.assertThatThrownBy(() -> underTest.getRestClientByName(NOT_EXISTING_CLIENT)) //
				.isInstanceOf(IllegalArgumentException.class) //
				.hasMessage("No element with name '" + NOT_EXISTING_CLIENT + "' configured in 'library.rest-clients'");
	}

}
