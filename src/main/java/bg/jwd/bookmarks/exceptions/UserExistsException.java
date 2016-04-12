package bg.jwd.bookmarks.exceptions;

public class UserExistsException extends Exception {

	private static final long serialVersionUID = 2681914800145153412L;

	public UserExistsException() { }
	
	public UserExistsException(String message) {
		super(message);
	}
}
