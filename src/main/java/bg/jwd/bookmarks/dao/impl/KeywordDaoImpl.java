package bg.jwd.bookmarks.dao.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import bg.jwd.bookmarks.dao.KeywordDao;
import bg.jwd.bookmarks.dao.generic.AbstractDao;
import bg.jwd.bookmarks.entities.Keyword;

@Service
public class KeywordDaoImpl extends AbstractDao<Keyword> implements KeywordDao{
	
	@Autowired
	private SessionFactory sessionFactory;

	public KeywordDaoImpl() {
		super(Keyword.class);
	}

	@Override
	public boolean addAll(Set<Keyword> keywords) {
		Transaction tx = null;
		boolean success = true;
		Session session = this.sessionFactory.openSession();
		
		try {
			tx = session.beginTransaction();
			// Transaction body
			for (Keyword keyword : keywords) {
				Criteria criteria = session.createCriteria(Keyword.class);
				criteria.add(Restrictions.like("keyword", keyword.getKeyword()));
				@SuppressWarnings("unchecked")
				List<Keyword> result = criteria.list();
				Keyword keyordFromDb = result.size() > 0 ? result.get(0) : null;
				
				if(keyordFromDb == null){
					session.save(keyword);
				}
			}
			tx.commit();
		}catch (HibernateException e) {
			tx.rollback();
			success = false;
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return success;
	}
}
