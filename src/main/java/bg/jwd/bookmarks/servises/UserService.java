package bg.jwd.bookmarks.servises;

import bg.jwd.bookmarks.dto.RegisterFormDto;
import bg.jwd.bookmarks.entities.User;
import bg.jwd.bookmarks.exceptions.UserExistsException;
import bg.jwd.bookmarks.services.generic.GenericService;

public interface UserService extends GenericService<User>{

	boolean disableUser(User userToDelete);
	
	boolean enableUser(User userToDelete);

	boolean addFollower(User user, User followerToAdd);

	boolean addFollowing(User user, User followingToAdd);

	User registerNewUserAccount(RegisterFormDto userDto) throws UserExistsException;

	User editExistingAccount(RegisterFormDto userDto);
}
