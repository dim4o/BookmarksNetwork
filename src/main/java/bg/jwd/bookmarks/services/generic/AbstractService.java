package bg.jwd.bookmarks.services.generic;

import java.io.Serializable;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import bg.jwd.bookmarks.dao.generic.GenericDao;

public class AbstractService<T extends Serializable> implements GenericService<T>{
	
	@Autowired
	SessionFactory sessionFactory;
	
	@Autowired
	private GenericDao<T> dao;
	
	@Override
	@Transactional
	public List<T> getAll(){
		return this.dao.getAll();
	}
	
	@Transactional
	@Override
	public T getByProperty(String propertyName, Object value){
		return dao.getByProperty(propertyName, value);
	}
	
	@Transactional
	@Override
	public List<T> getAllByProperty(String propertyName, Object value){
		return dao.getAllByProperty(propertyName, value);
	}
	
	@Transactional
	@Override
	public boolean add(T obj){
		return this.dao.add(obj) != null;
	}
	
	@Transactional
	@Override
	public boolean update(T obj){
		return dao.update(obj) != null;
	}
	
	@Transactional
	@Override
	public boolean delete(T obj){
		return dao.delete(obj);
	}

	/*@Override
	public boolean saveOrUpdate(T obj) {
		return this.dao.saveOrUpdate(obj) != null;
	}*/
}
