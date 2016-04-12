package bg.jwd.bookmarks.exceptions;

public class UnauthorizeException extends Exception {
	
	private static final long serialVersionUID = 7221338743135849806L;

	public UnauthorizeException() { }
	
	public UnauthorizeException(String message) {
		super(message);
	}
}
