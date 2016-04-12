package bg.jwd.bookmarks.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import bg.jwd.bookmarks.dao.KeywordDao;
import bg.jwd.bookmarks.dao.TagDao;
import bg.jwd.bookmarks.dao.UrlDao;
import bg.jwd.bookmarks.dto.AddBookmarkFormDto;
import bg.jwd.bookmarks.entities.Bookmark;
import bg.jwd.bookmarks.entities.Keyword;
import bg.jwd.bookmarks.entities.Tag;
import bg.jwd.bookmarks.entities.Url;
import bg.jwd.bookmarks.entities.User;
import bg.jwd.bookmarks.enums.VisibilityType;
import bg.jwd.bookmarks.services.generic.AbstractService;
import bg.jwd.bookmarks.servises.BookmarkService;
import bg.jwd.bookmarks.util.UserUtils;

@Service
public class BookmarkServiceImpl extends AbstractService<Bookmark> implements BookmarkService {
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	private KeywordDao keywordDao;
	
	@Autowired
	private TagDao tagDao;
	
	@Autowired
	private UrlDao urlDao;
	
	@Transactional
	@Override
	public Bookmark createBookmark(Bookmark bookmarkToAdd, AddBookmarkFormDto addBookmarkFormDto) throws Exception{
		//Bookmark bookmarkToAdd = null;
		String title = addBookmarkFormDto.getTitle();
		Url url = new Url(addBookmarkFormDto.getUrl());
		String description = addBookmarkFormDto.getDescription();
		Set<Keyword> keywords = this.mapToKeyordList(addBookmarkFormDto.getKeywords());
		Set<Tag> tags = this.mapToTagList((addBookmarkFormDto.getTags()));
		User author = UserUtils.getCurrentUser().getUser();
		VisibilityType visibility = VisibilityType.valueOf(addBookmarkFormDto.getVisibility());
		
		boolean keywordsAdded = this.keywordDao.addAll(keywords);
		boolean tagsAdded = this.tagDao.addAll(tags);
		urlDao.addIfNotExists(url);
		
		if(keywordsAdded && tagsAdded){
			bookmarkToAdd = new Bookmark(title, url, author, visibility);
			bookmarkToAdd.setDescription(description);
			bookmarkToAdd.setKeywords(keywords);
			bookmarkToAdd.setTags(tags);
			
			//bookmarkDao.add(bookmarkToAdd);
		} else {
			throw new Exception();
		}

		return bookmarkToAdd;
	}
	
	@Transactional
	@Override
	public Bookmark editBookmark(Bookmark bookmark, AddBookmarkFormDto form){
		Url url = new Url(form.getUrl());
		Set<Keyword> keywords = this.mapToKeyordList(form.getKeywords());
		Set<Tag> tags = this.mapToTagList(form.getTags());

		bookmark.setTitle(form.getTitle());
		bookmark.setDescription(form.getDescription());
		bookmark.setVisibility(VisibilityType.valueOf(form.getVisibility()));
		
		keywordDao.addAll(keywords);
		tagDao.addAll(tags);
		url = urlDao.addIfNotExists(url);
		
		bookmark.setUrl(url);
		
		Set<Keyword> keysFromDb = new HashSet<Keyword>();
		for (Keyword keyword : keywords) {
			Keyword k = keywordDao.getByProperty("keyword", keyword.getKeyword());
			keysFromDb.add(k);
		}
		
		Set<Tag> tagsFromDb = new HashSet<Tag>();
		for (Tag tag : tags) {
			Tag t = tagDao.getByProperty("tagName", tag.getTagName());
			tagsFromDb.add(t);
		}

		bookmark.setKeywords(keysFromDb);
		bookmark.setTags(tagsFromDb);

		return bookmark;
	}
	
	private Set<Keyword> mapToKeyordList(String wordsSequence){
		Set<Keyword> result = new HashSet<Keyword>();
		if(wordsSequence != null && !wordsSequence.isEmpty()){
			List<String> sequenseAsList = Arrays.asList(wordsSequence.trim().split("\\s*,\\s*"));
			
			for (String item : sequenseAsList) {
				Keyword itemToAdd = new Keyword(item);
				result.add(itemToAdd);
			}
		}

		return result;
	}
	
	private Set<Tag> mapToTagList(String wordsSequence){
		Set<Tag> result = new HashSet<Tag>();
		if(wordsSequence != null && !wordsSequence.isEmpty()){
			List<String> sequenseAsList = Arrays.asList(wordsSequence.trim().split("\\s*,\\s*"));
			
			for (String item : sequenseAsList) {
				Tag itemToAdd = new Tag(item);
				result.add(itemToAdd);
			}
		}
		
		return result;
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public List<Bookmark> getUserBookmarksWithPagination(int page, int pageSize, String username, boolean publicFilter){

		Session session = this.sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(Bookmark.class)
				.createAlias("author", "a");
		criteria.add(Restrictions.like("a.username", username));
		if(publicFilter){
			criteria.add(Restrictions.like("visibility", VisibilityType.Public));
		}
		criteria
			.addOrder(Order.desc("creationDate"))
			.addOrder(Order.asc("title"));
		
		//criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		//criteria.setFetchMode("lineItems", FetchMode.JOIN);
		//criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		
		criteria.setFirstResult((page - 1) * pageSize);
		criteria.setMaxResults(pageSize);
		List<Bookmark> result = criteria.list();

		return result;
	}
	
	@Override
	public int getCount(String username){
		int size = 0;
		Transaction tx = null; 
		Session session = this.sessionFactory.openSession();
		
		try{
			tx = session.beginTransaction();
			// Transaction body
			Criteria criteria = session.createCriteria(Bookmark.class)
					.createAlias("author", "a");
			criteria.add(Restrictions.like("a.username", username));
			size =  criteria.list().size();
			tx.commit();
		} catch(HibernateException e){
			e.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
		
		return size;
	}

	
	
	/**
	 * Deletes bookmark
	 * Deletes url only if hasn't other bookmarks associated
	 */
	@Transactional
	@Override
	public boolean safeDeleteBookmark(Bookmark bookmarkToDelete) {
		Url url = bookmarkToDelete.getUrl();
		url.getBookmarks().remove(bookmarkToDelete);
		boolean bookmarkDeleted = this.delete(bookmarkToDelete);
		
		if(url.getBookmarks().size() == 0){
			urlDao.delete(url);
		}
		
		return bookmarkDeleted;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Bookmark> getAllUserBookmarks(String username) {
		List<Bookmark> result = null;
		Transaction tx = null; 
		Session session = this.sessionFactory.openSession();
		
		try{
			tx = session.beginTransaction();
			// Transaction body
			Criteria criteria = session.createCriteria(Bookmark.class)
					.createAlias("author", "a");
			criteria.add(Restrictions.like("a.username", username));
			result =  criteria.list();
			tx.commit();
		} catch(HibernateException e){
			e.printStackTrace();
			tx.rollback();
		} finally {
			session.close();
		}
		
		return result;
	}
}
