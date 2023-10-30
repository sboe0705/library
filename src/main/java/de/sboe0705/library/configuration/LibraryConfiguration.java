package de.sboe0705.library.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Component
@ConfigurationProperties("library")
@Validated
public class LibraryConfiguration {

	private List<RestClientConfiguration> restClients;

	public List<RestClientConfiguration> getRestClients() {
		return restClients;
	}

	public void setRestClients(List<RestClientConfiguration> restClients) {
		this.restClients = restClients;
	}

	public RestClientConfiguration getRestClientByName(String name) {
		return restClients.stream() //
				.filter(restClient -> name.equalsIgnoreCase(restClient.getName())) //
				.findFirst() //
				.orElseThrow(() -> new IllegalArgumentException("No element with name '" + name + "' configured in 'library.rest-clients'"));
	}

}
