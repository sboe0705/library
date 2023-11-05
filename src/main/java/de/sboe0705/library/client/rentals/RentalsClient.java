package de.sboe0705.library.client.rentals;

import java.util.List;
import java.util.Map;

import de.sboe0705.library.client.ClientErrorException;
import de.sboe0705.library.client.rentals.gen.model.Rent;

public interface RentalsClient {

	public static final String CLIENT_NAME = "rentals";

	List<Rent> getRents(boolean onlyRent);

	Boolean isItemRent(long itemId) throws ClientErrorException;

	Map<Long, Boolean> areItemsRent(List<Long> itemIds);

	void rentItem(long itemId, String userId);

	void returnItem(long itemId);

}
