package bg.jwd.bookmarks.servises;

import java.util.List;

import bg.jwd.bookmarks.dto.BookmarkSearchDto;
import bg.jwd.bookmarks.dto.UserSearchDto;
import bg.jwd.bookmarks.entities.Bookmark;
import bg.jwd.bookmarks.entities.User;

public interface SearchService {
	
	List<Bookmark> experimentalBookmarksSearch(String keyword);
	
	List<Bookmark> searchBookmarks(BookmarkSearchDto searchForm);
	
	List<Bookmark> searchBookmarks(String keyword);
	
	List<User> userSearch(UserSearchDto searchParams);
}
