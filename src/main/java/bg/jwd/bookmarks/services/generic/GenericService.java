package bg.jwd.bookmarks.services.generic;

import java.util.List;

public interface GenericService<T> {
	
	List<T> getAll();

	T getByProperty(String propertyName, Object value);
	
	List<T> getAllByProperty(String propertyName, Object value);
	
	boolean add(T obj);
	
	boolean update(T obj);
	
	boolean delete(T obj);
	
	//boolean saveOrUpdate(T obj);
}
