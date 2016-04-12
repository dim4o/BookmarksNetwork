package bg.jwd.bookmarks.dao.impl;

import org.springframework.stereotype.Repository;

import bg.jwd.bookmarks.dao.BookmarkDao;
import bg.jwd.bookmarks.dao.generic.AbstractDao;
import bg.jwd.bookmarks.entities.Bookmark;

@Repository
public class BookmarkDaoImpl extends AbstractDao<Bookmark> implements BookmarkDao {

	/*@Autowired
	private SessionFactory sessionFactory;*/
	
	/*@Autowired
	private UrlDao urlDao;
	
	@Autowired
	private BookmarkDao bookmarkDao;*/

	public BookmarkDaoImpl() {
		super(Bookmark.class);
	}
}
