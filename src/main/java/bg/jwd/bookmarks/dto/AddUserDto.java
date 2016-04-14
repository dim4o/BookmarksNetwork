package bg.jwd.bookmarks.dto;

import java.util.List;

public class AddUserDto extends RegisterFormDto{
	
	private boolean status;
	
	private List<String> roles;

	public boolean getStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
