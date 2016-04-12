package bg.jwd.bookmarks.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.jwd.bookmarks.dao.RoleDao;
import bg.jwd.bookmarks.dao.UserDao;
import bg.jwd.bookmarks.dto.RegisterFormDto;
import bg.jwd.bookmarks.entities.Role;
import bg.jwd.bookmarks.entities.User;
import bg.jwd.bookmarks.exceptions.UserExistsException;
import bg.jwd.bookmarks.services.generic.AbstractService;
import bg.jwd.bookmarks.servises.UserService;
import bg.jwd.bookmarks.util.UserUtils;

@Service
public class UserServiceImpl extends AbstractService<User> implements UserService{
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private RoleDao roleDao;

	@Transactional
	@Override
	public List<User> getAll() {
		return userDao.getAll();
	}

	/*@Transactional
	@Override
	public boolean add(User userToAdd) {
		return userDao.add(userToAdd) != null;
	}*/

	@Transactional
	@Override
	public boolean disableUser(User user) {	
		user.setEnabled(false);
		return userDao.update(user) != null;
	}
	
	@Transactional
	@Override
	public boolean enableUser(User user) {	
		user.setEnabled(true);
		return userDao.update(user) != null;
	}

	@Transactional
	@Override
	public boolean update(User userToUpdate) {
		return userDao.update(userToUpdate) != null;
	}

	@Transactional
	@Override
	public boolean addFollower(User user, User followerToAdd) {
		user.getFollowersCollection().add(followerToAdd);
		
		return userDao.update(user) != null;
	}

	@Transactional
	@Override
	public boolean addFollowing(User user, User followingToAdd) {
		user.getFollowingCollection().add(followingToAdd);
		
		return userDao.update(user) != null;
	}

	@Transactional
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public User registerNewUserAccount(RegisterFormDto userDto) 
			throws UserExistsException {
		if(this.userExists(userDto.getEmail())){
			throw new UserExistsException(
					"There is an account with that username:" + userDto.getUsername());
		}
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String encodedPassword = encoder.encodePassword(userDto.getPassword(), null);
		User userToAdd = new User(
				userDto.getUsername(), 
				encodedPassword, 
				userDto.getEmail(), 
				userDto.getFirstName(), 
				userDto.getLastName(), 
				userDto.getAddress());
		
		Role userRole = roleDao.getByProperty("roleName", "user");
		List roles = new ArrayList<Role>();
		roles.add(userRole);
		userToAdd.setRoles(roles);

		userDao.add(userToAdd);
		
		return userToAdd;
	}
	
	// TODO: Move to AbstractDao -> isPropertyExists(String propertyName, Object value)
	@Transactional
	private boolean userExists(String username){
		User user = userDao.getByProperty("username", username);
		if(user != null){
			return true;
		}
		
		return false;
	}
	
	// TODO: Move to AbstractDao -> isPropertyExists(String propertyName, Object value)
	@Transactional
	private boolean emailExists(String email){
		User user = userDao.getByProperty("email", email);
		if(user != null){
			return true;
		}
		
		return false;
	}

	@Transactional
	@Override
	public User editExistingAccount(RegisterFormDto form) {
		Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		String encodedPassword = encoder.encodePassword(form.getPassword(), null);
		
		User currentUser = UserUtils.getCurrentUser().getUser();
		String currentUserUsername = currentUser.getUsername();
		
		if(true){
			
		}
		
		currentUser.setUsername(form.getUsername());
		currentUser.setEmail(form.getEmail());
		currentUser.setFirstName(form.getFirstName());
		currentUser.setLastName(form.getLastName());
		currentUser.setAddress(form.getAddress());
		
		if(form.getPassword() != null){
			currentUser.setPassword(encodedPassword);
		}

		userDao.update(currentUser);
		
		return currentUser;
	}

	@Transactional
	@Override
	public User getByProperty(String propertyName, Object value) {
		return userDao.getByProperty(propertyName, value);
	}
}
