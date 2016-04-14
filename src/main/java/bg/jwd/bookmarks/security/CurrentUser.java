package bg.jwd.bookmarks.security;
import bg.jwd.bookmarks.entities.User;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

	private static final long serialVersionUID = -2137583252465681895L;
	private User user;

	public CurrentUser(User user, Set<GrantedAuthority> authorities) {
		super(user.getUsername(), user.getPassword(), authorities);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
