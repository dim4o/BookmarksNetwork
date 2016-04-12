package bg.jwd.bookmarks.dao.impl;

import org.springframework.stereotype.Repository;

import bg.jwd.bookmarks.dao.UserDao;
import bg.jwd.bookmarks.dao.generic.AbstractDao;
import bg.jwd.bookmarks.entities.User;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements UserDao{
	
	public UserDaoImpl(){
		super(User.class);
	}
}
