package bg.jwd.bookmarks.servises;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import bg.jwd.bookmarks.dto.AddBookmarkFormDto;
import bg.jwd.bookmarks.entities.Bookmark;
import bg.jwd.bookmarks.services.generic.GenericService;

public interface BookmarkService extends GenericService<Bookmark> {
	
	Bookmark createBookmark(Bookmark bookmarkToAdd, AddBookmarkFormDto addBookmarkFormDto) throws Exception;
	
	Bookmark editBookmark(Bookmark bookmark, AddBookmarkFormDto form);
	
	public List<Bookmark> getUserBookmarksWithPagination(int page, int pageSize, String username, boolean publicFilter);
	
	public List<Bookmark> getAllUserBookmarks(String username);
	
	long getCount(String username);
	
	boolean safeDeleteBookmark(Bookmark bookmark);
	
	void importBookmarks(HttpServletRequest request, MultipartFile file, String visibility) throws Throwable;
}
