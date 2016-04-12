package bg.jwd.bookmarks.dao.generic;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractDao<T extends Serializable> implements GenericDao<T>{
	
	@Autowired
	private SessionFactory sessionFactory;

	private Class<T> clazz;
	
	protected AbstractDao(){ }
	
	protected AbstractDao(Class<T> clazz){
		this.clazz = clazz;
	}
	
	public  void setClass(Class<T> clazz){
		this.clazz = clazz;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAllByProperty(String propertyName, Object value) {
		Session session = this.getCurrentSession();
		Criteria criteria = session.createCriteria(this.clazz);
		criteria.add(Restrictions.like(propertyName, value)); // TODO: like !
		List<T> result = criteria.list();

		return result;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public T getByProperty(String propertyName, Object value) {
		final Session session = this.getCurrentSession();
		Criteria criteria = session.createCriteria(this.clazz);
		criteria.add(Restrictions.like(propertyName, value));
		List<T> result = criteria.list();
		if(result.size() == 0){
			return null;
		}
		return result.get(0);
	}

	@Override
	public T add(final T obj) {
		final Session session = this.getCurrentSession();
		@SuppressWarnings("unchecked")
		T result = (T) session.save(obj);
		
		return result;
	}

	public boolean delete(final Object object) {
		final Session session = this.getCurrentSession();
		session.delete(object);
		
		// TODO: correct this
		return true;
	}

	@Override
	public T get(final Long id) {
		Session session = this.getCurrentSession();
		T result = session.get(this.clazz, id);

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
		Session session = this.getCurrentSession();
		/*if(true){
			throw new HibernateException("Message");
		}*/
		Criteria criteria = session.createCriteria(this.clazz);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<T> result = criteria.list();
	
		return result;
	}

	@Override
	public T update(T obj) {
		Session session = this.getCurrentSession();
		session.update(obj);
		
		return obj;
	}
	
	private Session getCurrentSession() {
		return this.sessionFactory.getCurrentSession();
	}
}