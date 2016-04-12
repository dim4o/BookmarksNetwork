package bg.jwd.bookmarks.dao;

import java.util.Set;

import bg.jwd.bookmarks.dao.generic.GenericDao;
import bg.jwd.bookmarks.entities.Tag;

public interface TagDao extends GenericDao<Tag>{
	
	boolean addAll(Set<Tag> tags);
}
