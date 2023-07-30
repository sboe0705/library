package libraryde.sboe0705.library.model;

import jakarta.validation.constraints.NotBlank;

public class Book {
	
	private Long id;
	
	@NotBlank
	private String firstName;
	
	@NotBlank
	private String lastName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
