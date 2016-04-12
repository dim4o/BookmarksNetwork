package bg.jwd.bookmarks.constants;

public class UrlContants {
	
	public static final String BOOKMARKS_CONTROLLER_URL = "/user/bookmarks";
	
	public static final String USER_CONTROLLER_URL = "/user";
	
	public static final String LOGIN_CONTROLLER_URL = "/login";
	
	public static final String REGISTER_CONTROLLER_URL = "/register";
	
	public static final String EDIT_BOOKMARK_URL = "/edit/{bookmarkId:[0-9]+}";
	
	public static final String DELETE_BOOKMARK_URL = "/delete/{bookmarkId:[0-9]+}";
	
	public static final String PAGINATION_URL = "page/{pageNum:[0-9]+}/from/{totalNum:[0-9]+}";
	
	public static final String ADD_ACTION = "/add";
	
	public static final String ADMIN_CONTROLLER_URL = "/admin";
	
	public static final String USERS_URL = "/users";
	
	public static final String IMPORT_ACTION_URL = "/import";
	
	public static final String ALL_USERS_URL = "/admin/users";
	
	public static final String EDIT_USER_ACTION_URL = "/users/edit/{userId:[0-9]+}";
	
	public static final String EDIT_USER_ADD_ROLE_ACTION_URL = "/users/edit/addRole/{userId:[0-9]+}";
	
	public static final String DELETE_ROLE_ACTION_URL = "/users/edit/deleteRole/{userId:[0-9]+}/{roleName}";
	
	public static final String ENABLE_USER_ACTION_URL = "/users/edit/enable/{userId:[0-9]+}";
	
	public static final String DISABLE_USER_ACTION_URL = "/users/edit/disable/{userId:[0-9]+}";
	
	public static final String ADD_USER_ACTION_URL = "/users/add";
	
	public static final String EDIT_USER_ACTION_REDIRECT_URL = "/admin/users/edit/{userId}";
	
	public static final String EDIT_USER_PROFILE_ACTION = "/editProfile";
	
	public static final String USER_PROFILE = "/profile/{username:[A-Za-z0-9]+}";
	
	public static final String USER_PROFILE_PAGINATION_URL = "/profile/{username}/page/{pageNum}/from/{totalNum}";
	
	public static final String USER_FOLLOWERS_URL = "/followers";
	
	public static final String USER_FOLLOWINGS_URL = "/followings";
	
	public static final String USER_FOLLOW_ACTION_URL = "/follow/{userId:[0-9]+}";
	
	public static final String USER_UNFOLLOW_ACTION_URL = "/unfollow/{userId:[0-9]+}";
}
