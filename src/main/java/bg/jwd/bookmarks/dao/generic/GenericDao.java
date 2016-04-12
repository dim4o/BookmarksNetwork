package bg.jwd.bookmarks.dao.generic;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T extends Serializable> {
	
	List<T> getAllByProperty(String propertyName, Object value);
	
	//List<T> getAllWithPagination(int pageNumber, int pageSize, String propertyName, Object value);
	
	void setClass(Class<T> clazz);
	
	T getByProperty(String propertyName, Object value);

	T add(final T obj);

	boolean delete(final Object object);

	T get(final Long id);

	T update(final T obj);
	
	T merge(final T obj);

	//T saveOrUpdate(final T ubj);

	List<T> getAll();

	//void setClass(Class<T> clazz);
}
