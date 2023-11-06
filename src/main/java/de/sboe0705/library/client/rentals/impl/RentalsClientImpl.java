package de.sboe0705.library.client.rentals.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import de.sboe0705.library.client.ClientErrorException;
import de.sboe0705.library.client.rentals.RentalsClient;
import de.sboe0705.library.client.rentals.gen.api.RentalsControllerApi;
import de.sboe0705.library.client.rentals.gen.model.Rent;

@Service
public class RentalsClientImpl implements RentalsClient {

	@Autowired
	private RentalsControllerApi rentalsControllerApi;

	@Override
	public List<Rent> getRents(boolean onlyRent) throws ClientErrorException {
		try {
			return rentalsControllerApi.getRents(onlyRent);
		} catch (HttpClientErrorException e) {
			throw new ClientErrorException(CLIENT_NAME, e);
		}
	}

	@Override
	public Boolean isItemRent(long itemId) throws ClientErrorException {
		try {
			return rentalsControllerApi.isItemRent(itemId);
		} catch (HttpClientErrorException e) {
			throw new ClientErrorException(CLIENT_NAME, e);
		}
	}

	@Override
	public Map<Long, Boolean> areItemsRent(List<Long> itemIds) throws ClientErrorException {
		try {
			String itemIdsAsCommaSeparatedString = itemIds.stream() //
					.map(Object::toString) //
					.collect(Collectors.joining(","));
			Map<String, Boolean> items = rentalsControllerApi.areItemsRent(itemIdsAsCommaSeparatedString);
			return items.entrySet().stream().collect(Collectors.toMap( //
					entry -> Long.parseLong(entry.getKey()), //
					entry -> entry.getValue() //
			));
		} catch (HttpClientErrorException e) {
			throw new ClientErrorException(CLIENT_NAME, e);
		}
	}

	@Override
	public void rentItem(long itemId, String userId) throws ClientErrorException {
		try {
			rentalsControllerApi.rentItem(itemId, userId);
		} catch (HttpClientErrorException e) {
			throw new ClientErrorException(CLIENT_NAME, e);
		}
	}

	@Override
	public void returnItem(long itemId) throws ClientErrorException {
		try {
			rentalsControllerApi.returnItem(itemId);
		} catch (HttpClientErrorException e) {
			throw new ClientErrorException(CLIENT_NAME, e);
		}
	}

}
