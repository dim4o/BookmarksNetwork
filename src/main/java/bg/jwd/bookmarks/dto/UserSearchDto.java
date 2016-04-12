package bg.jwd.bookmarks.dto;

public class UserSearchDto {
	
	private String searchTerm;
	
	private boolean usernameSearch;
	
	private boolean emailSearch;
	
	private boolean firstNameSearch;
	
	private boolean lastNameSearch;

	public String getSearchTerm() {
		return searchTerm;
	}

	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	
	public boolean isUsernameSearch() {
		return usernameSearch;
	}
	
	public void setLastNameSearch(boolean lastNameSearch) {
		this.lastNameSearch = lastNameSearch;
	}

	public void setUsernameSearch(boolean usernameSearch) {
		this.usernameSearch = usernameSearch;
	}

	public boolean isEmailSearch() {
		return emailSearch;
	}

	public void setEmailSearch(boolean emailSearch) {
		this.emailSearch = emailSearch;
	}

	public boolean isFirstNameSearch() {
		return firstNameSearch;
	}

	public void setFirstNameSearch(boolean firstNameSearch) {
		this.firstNameSearch = firstNameSearch;
	}

	public boolean isLastNameSearch() {
		return lastNameSearch;
	}
}
