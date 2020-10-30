package shopmanagementsystem.categoryManagement;

import java.util.Collection;

public interface CategoryDao<T> {

	Collection<T> getAll();
	Collection<T> getAllByIntitule(String intitule);
	Boolean add(T category);
	Boolean update(T category, long id);
	Boolean delete(long id);
	T getOne(long id);
	T getOneByIntitule(String intitule);
}
