/*package bg.jwd.bookmarks.dao.generic;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
//@Transactional
public abstract class AbstractDaoOld<T extends Serializable> implements GenericDao<T>{

	@Autowired
	private HibernateDaoSupport hibernateDaoSupport;
	
	@Autowired
	private SessionFactory sessionFactory;

	private Class<T> clazz;
	
	protected AbstractDaoOld(){ }
	
	protected AbstractDaoOld(Class<T> clazz){
		this.clazz = clazz;
	}
	
	public  void setClass(Class<T> clazz){
		this.clazz = clazz;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAllByProperty(String propertyName, Object value) {
		Transaction tx = null;
		List<T> result = null;
		Session session = this.openSession();
		
		try{
			tx = session.beginTransaction();
			// Transaction body
			Criteria criteria = this.openSession().createCriteria(this.clazz);
			criteria.add(Restrictions.like(propertyName, value)); // TODO: like !
			result = criteria.list();
			
			tx.commit();
		} catch(HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getByProperty(String propertyName, Object value) {
		Transaction tx = null;
		List<T> result = null;
		final Session session = this.openSession();
		
		try {
			tx = session.beginTransaction();
			// Transaction body
			Criteria criteria = session.createCriteria(this.clazz);
			criteria.add(Restrictions.like(propertyName, value));
			result = criteria.list();
			
			tx.commit();
		} catch (HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		if(result.size() == 0){
			return null;
		}
		
		return result.get(0);
	}

	@Override
	public T add(final T obj) {
		Transaction tx = null;
		T result = null;
		final Session session = this.openSession();
		
		try{
			tx = session.beginTransaction();
			// Transaction body
			session.save(obj);
			session.flush();
			tx.commit();
		} catch(HibernateException e) {
			tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return result;
	}

	public boolean delete(final Object object) {
		Transaction tx = null;
		boolean delete = true;
		final Session session = this.openSession();
		
		try{
			tx = session.beginTransaction();
			// Transaction body
			session.delete(object);
			tx.commit();
		} catch(HibernateException e){
			tx.rollback();
			delete = false;
			e.printStackTrace();
		} finally{
			session.close();
		}
		
		return delete;
	}

	@Override
	public T get(final Long id) {
		
		Transaction tx = null;
		T result = null;
		Session session = this.openSession();
		
		try{
			tx = session.beginTransaction();
			// Transaction body
			result = session.get(this.clazz, id);
			tx.commit();
		} catch(HibernateException e){
			
		} finally {
			session.close();
		}
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T merge(final T o) {
		return (T) this.getCurrentSession().merge(o);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		Transaction tx = null;
		List<T> result = null;
		Session session = this.openSession();
		
		try{
			tx = session.beginTransaction();
			// Transaction body
			Criteria criteria = session.createCriteria(this.clazz);
			criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
			result = criteria.list();
			tx.commit();
		} catch(HibernateException e){
			tx.rollback();
			e.printStackTrace();
		} finally{
			session.close();
		}

		return result;
	}

	@Override
	public T update(T obj) {
		Transaction tx = null;
		T result = null;
		Session session = this.openSession();
		
		try{
			tx = session.beginTransaction();
			// Transaction body
			session.update(obj);
			result = obj;
			tx.commit();
		} catch(HibernateException e){
			
		} finally {
			session.close();
		}
		
		return result;
	}
	
	private Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}
	
	private Session openSession() {
		return this.sessionFactory.openSession();
	}
}*/