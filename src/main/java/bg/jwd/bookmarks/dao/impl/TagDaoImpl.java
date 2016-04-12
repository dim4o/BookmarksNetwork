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

import bg.jwd.bookmarks.dao.TagDao;
import bg.jwd.bookmarks.dao.generic.AbstractDao;
import bg.jwd.bookmarks.entities.Tag;

@Service
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	public TagDaoImpl(){
		super(Tag.class);
	}

	// TODO: Makes Transactional and void
	@Override
	public boolean addAll(Set<Tag> tags) {
		Transaction tx = null;
		boolean success = true;
		Session session = this.sessionFactory.openSession();
		
		try {
			tx = session.beginTransaction();
			// Transaction body
			for (Tag tag : tags) {
				Criteria criteria = session.createCriteria(Tag.class);
				criteria.add(Restrictions.like("tagName", tag.getTagName()));
				@SuppressWarnings("unchecked")
				List<Tag> result = criteria.list();
				Tag tagFromDb = result.size() > 0 ? result.get(0) : null;
				
				if(tagFromDb == null){
					session.save(tag);
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
