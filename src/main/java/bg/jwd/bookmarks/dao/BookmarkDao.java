package bg.jwd.bookmarks.dao;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import bg.jwd.bookmarks.dao.generic.GenericDao;
import bg.jwd.bookmarks.entities.Bookmark;

public interface BookmarkDao extends GenericDao<Bookmark>{

	void importBookmarks(HttpServletRequest request, MultipartFile file, String visibility) throws Throwable;
}
