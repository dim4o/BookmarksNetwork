package bg.jwd.bookmarks.servises;

import java.util.List;
import java.util.Set;

import bg.jwd.bookmarks.entities.Keyword;

public interface KeywordService {
	
	List<Keyword> getAsCollection(String keywords);
	
	boolean addAll(Set<Keyword> keyword);
}
