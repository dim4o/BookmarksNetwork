package bg.jwd.bookmarks.services.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.aspectj.lang.annotation.AfterReturning;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.hibernate.search.query.dsl.TermMatchingContext;
import org.hibernate.search.query.dsl.WildcardContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.jwd.bookmarks.dao.BookmarkDao;
import bg.jwd.bookmarks.dto.BookmarkSearchDto;
import bg.jwd.bookmarks.dto.UserSearchDto;
import bg.jwd.bookmarks.entities.Bookmark;
import bg.jwd.bookmarks.entities.User;
import bg.jwd.bookmarks.servises.SearchService;
import bg.jwd.bookmarks.util.UserUtils;

@Service
public class SearchServiceImpl implements SearchService{
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	BookmarkDao bookmarkDao;
	
	private org.hibernate.search.query.dsl.TermContext createTermContext(FullTextSession fullTextSession){
		org.hibernate.search.query.dsl.TermContext termContext = null;
		try {
			fullTextSession.createIndexer().startAndWait();
			
			QueryBuilder qb = fullTextSession.getSearchFactory()
					.buildQueryBuilder().
					forEntity(Bookmark.class).get();
			
			termContext = qb.keyword();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return termContext;
	}
	
	@SuppressWarnings("unchecked")
	@AfterReturning(pointcut = "execution(* bg.jwd.bookmarks..*.*(..))", returning = "retVal")
	@Override
	public List<Bookmark> experimentalBookmarksSearch(String keyword) {
		
		String term = "*" + keyword + '*';
		List<Bookmark> result = null;
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		FullTextSession fullTextSession = Search.getFullTextSession(session);
		
		try {
			fullTextSession.createIndexer().startAndWait();
			
			QueryBuilder qb = fullTextSession.getSearchFactory()
					.buildQueryBuilder().
					forEntity(Bookmark.class).get();
			
			org.apache.lucene.search.Query luceneQuery = qb
					  .keyword().wildcard()
					  .onField("title").andField("keywords.keyword").andField("tags.tagName").andField("description")
					  .matching(term.toLowerCase())
					  .createQuery();
			
			FullTextQuery fullTextQuery =
					fullTextSession.createFullTextQuery(luceneQuery, Bookmark.class);
					
			result = fullTextQuery.list();
			tx.commit();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return result;
	}
	
	@SuppressWarnings({ "null", "unchecked" })
	@Override
	public List<Bookmark> searchBookmarks(BookmarkSearchDto searchForm){
		if(searchForm.getSearchTerm() == null || searchForm.getSearchTerm().isEmpty()){
			return null;
		}
		String term = "*" + searchForm.getSearchTerm() + "*";
		List<Bookmark> result = null;
		Transaction tx = null;
		Session session = sessionFactory.openSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		
		try{
			tx = session.beginTransaction();
			
			WildcardContext termContext = createTermContext(fullTextSession).wildcard();
			boolean start = false;
			TermMatchingContext tmc = null;
			
			if(searchForm.isTitleSearch()){
				if(!start){
					tmc = termContext.onField("title");
				} else {
					tmc.andField("title");
				}
				start = true;
			}
			
			if(searchForm.isKeywordSearch()){
				if(!start){
					tmc = termContext.onField("keywords.keyword");
				} else {
					tmc.andField("keywords.keyword");
				}
				start = true;
			}
			
			if(searchForm.isTagSearch()){
				if(!start){
					tmc = termContext.onField("tags.tagName");
				} else {
					tmc.andField("tags.tagName");
				}
				start = true;
			}
			
			if(searchForm.isDescriptionSearch()){
				if(!start){
					tmc = termContext.onField("description");
				} else {
					tmc.andField("description");
				}
				start = true;
			}
			
			if(tmc != null){
				org.apache.lucene.search.Query luceneQuery = tmc.matching(term.toLowerCase()).createQuery();
				FullTextQuery fullTextQuery =
						fullTextSession.createFullTextQuery(luceneQuery, Bookmark.class);
				
				if(!searchForm.isDescriptionSearch()){
					String currentUserUsername = UserUtils.getCurrentUser().getUsername();
					Criteria cr = session.createCriteria(Bookmark.class).createAlias("author", "author");
					cr.add(Restrictions.like("author.username", currentUserUsername));
					fullTextQuery.setCriteriaQuery(cr);
				}
				
				result = fullTextQuery.list();
			}
			tx.commit();
		} catch(HibernateException e){
			tx.rollback();
			e.printStackTrace();
		}
		
		return result;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<Bookmark> searchBookmarks(String keyword) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Set<Bookmark> set = new HashSet<Bookmark>();

		Criteria criteria0 = session.createCriteria(Bookmark.class)
				.add(Restrictions.like("title", keyword + "%").ignoreCase());

		Criteria criteria1 = session.createCriteria(Bookmark.class).createAlias("tags", "tagsAlias")
				.add(Restrictions.like("tagsAlias.tagName", keyword + "%").ignoreCase());

		Criteria criteria2 = session.createCriteria(Bookmark.class).createAlias("keywords", "keywordsAlias")
				.add(Restrictions.like("keywordsAlias.keyword", keyword + "%").ignoreCase());

		criteria0.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria1.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		criteria2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		List result0 = criteria0.list();
		List result1 = criteria1.list();
		List result2 = criteria1.list();
		set.addAll(result0);
		set.addAll(result1);
		set.addAll(result2);

		tx.commit();
		session.close();

		if (set.size() == 0) {
			return null;
		}

		List list = new ArrayList<Bookmark>();

		list.addAll(set);

		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> userSearch(UserSearchDto searchParams){
		List<User> result = null;
		String term = "%" + searchParams.getSearchTerm() + "%";
		
		Session session = this.sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
			
		try {
			Criterion criterion = null;
			Criteria criteria = session.createCriteria(User.class);
			criterion = chainCriterion(searchParams.isUsernameSearch(), criterion, term, "username", searchParams);
			criterion = chainCriterion(searchParams.isEmailSearch(), criterion, term, "email", searchParams);
			criterion = chainCriterion(searchParams.isFirstNameSearch(), criterion, term, "firstName", searchParams);
			criterion = chainCriterion(searchParams.isLastNameSearch(), criterion, term, "lastName", searchParams);
			criteria.add(criterion);
			
			result = criteria.list();
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}

		return result;
	}

	/**
	 * @param searchParams
	 * @param term
	 * @param criterion
	 * @return Criterion with chained "or"
	 */
	private Criterion chainCriterion(
			boolean SearchBy,
			Criterion criterion, 
			String term, 
			String propertyName, 
			UserSearchDto searchParams) {
		if(SearchBy){
			Criterion criteria = Restrictions.like(propertyName, term);
			if(criterion == null){
				criterion = Restrictions.or(criteria);
			} else {
				criterion = Restrictions.or(criterion, criteria);
			}
		}
		return criterion;
	}
}
