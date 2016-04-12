package bg.jwd.bookmarks.util;

import org.springframework.security.core.context.SecurityContextHolder;

import bg.jwd.bookmarks.security.CurrentUser;

public class UserUtils {

	private UserUtils() {
	}

	public static CurrentUser getCurrentUser() {
		Object principal;

		try {
			principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (NullPointerException e) {
			return null;
		}

		if (principal == null || !(principal instanceof CurrentUser)) {
			return null;
		}

		return (CurrentUser) principal;
	}
}