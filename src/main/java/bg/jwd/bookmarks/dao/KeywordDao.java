package bg.jwd.bookmarks.dao;

import java.util.Set;

import bg.jwd.bookmarks.dao.generic.GenericDao;
import bg.jwd.bookmarks.entities.Keyword;

public interface KeywordDao extends GenericDao<Keyword>{
	
	void addAll(Set<Keyword> keywords);
}
