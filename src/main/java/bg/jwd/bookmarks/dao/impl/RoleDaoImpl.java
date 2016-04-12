package bg.jwd.bookmarks.dao.impl;

import org.springframework.stereotype.Repository;

import bg.jwd.bookmarks.dao.RoleDao;
import bg.jwd.bookmarks.dao.generic.AbstractDao;
import bg.jwd.bookmarks.entities.Role;


@Repository
public class RoleDaoImpl extends AbstractDao<Role> implements RoleDao{
	
	public RoleDaoImpl(){
		super(Role.class);
	}
}
