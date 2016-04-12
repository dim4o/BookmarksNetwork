package bg.jwd.bookmarks.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import bg.jwd.bookmarks.validations.PasswordMatches;
import bg.jwd.bookmarks.validations.AlreadyExists;

@PasswordMatches

public class RegisterFormDto {	
	
	private String firstName;

	private String lastName;
	
	@AlreadyExists(propertyName = "username", message = "Username alrady exists!")
	@NotNull
	@NotEmpty
	private String username;
	
	@AlreadyExists(propertyName = "email", message = "Email alrady exists!")
	@Email
	@NotEmpty
	private String email;
	
	@NotNull
	@NotEmpty
	@Size(min = 6, max = 15, message = "Your password must between 6 and 15 characters")
	private String password;
	
	@NotNull
	@NotEmpty
	@Size(min = 6, max = 15, message = "Your password must between 6 and 15 characters")
	private String matchingPassword;
	
	private String address;

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

	public String getPassword() {
		return password;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMatchingPassword() {
		return matchingPassword;
	}

	public void setMatchingPassword(String matchingPassword) {
		this.matchingPassword = matchingPassword;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
