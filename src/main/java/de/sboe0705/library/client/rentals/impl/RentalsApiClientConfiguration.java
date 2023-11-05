package de.sboe0705.library.client.rentals.impl;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import de.sboe0705.library.client.rentals.RentalsClient;
import de.sboe0705.library.client.rentals.gen.api.RentalsControllerApi;
import de.sboe0705.library.client.rentals.gen.invoker.ApiClient;
import de.sboe0705.library.configuration.LibraryConfiguration;
import de.sboe0705.library.configuration.RestClientConfiguration;

@Configuration
public class RentalsApiClientConfiguration {

	@Bean
	public ApiClient getApiClient(RestTemplateBuilder restTemplateBuilder, LibraryConfiguration libraryConfiguration) {
		RestTemplate restTemplate = restTemplateBuilder.build();
		ApiClient apiClient = new ApiClient(restTemplate);
		RestClientConfiguration restClientConfiguration = libraryConfiguration
				.getRestClientByName(RentalsClient.CLIENT_NAME);
		apiClient.setBasePath(restClientConfiguration.getBaseURI().toASCIIString());
		return apiClient;
	}

	@Bean
	public RentalsControllerApi getRentalsControllerApi(ApiClient apiClient) {
		return new RentalsControllerApi(apiClient);
	}

}
