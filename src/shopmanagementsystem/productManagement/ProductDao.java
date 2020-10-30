package shopmanagementsystem.productManagement;

import java.util.Collection;

import shopmanagementsystem.categoryManagement.Category;

public interface ProductDao<T> {

	Collection<T> getAll();
	Collection<T> getAllByDesignation(String designation);
	Boolean add(T product);
	Boolean update(T product, long id);
	Boolean delete(T product);
	Collection<T> getAllByCategory(Category category);
	T getOne(long id);
}
