package de.sboe0705.library.model;

import jakarta.validation.constraints.NotNull;

public class User {

	@NotNull
	private String id;

	@NotNull
	private String firstName;

	@NotNull
	private String lastName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
