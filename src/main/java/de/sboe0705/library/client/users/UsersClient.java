package de.sboe0705.library.client.users;

import java.util.List;

import de.sboe0705.library.model.User;

public interface UsersClient {

	List<User> getUsers();

	User getUser(String id);

}
