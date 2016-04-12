package bg.jwd.bookmarks.dao;

import bg.jwd.bookmarks.dao.generic.GenericDao;
import bg.jwd.bookmarks.entities.Url;

public interface UrlDao extends GenericDao<Url>{
	
	Url addIfNotExists(Url url);
}
