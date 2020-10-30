package shopmanagementsystem.ItemManagement;

import java.util.Collection;

public interface ItemDao<T> {

	Collection<T> getAll();
	Boolean add(Item item);
	Boolean addItem(Item item);
	Boolean update(Item item, long id);
	Boolean delete(long id);
	Boolean deleteAllByIdSale(long id);
	T getOne(long id);
	T getOneByDesignation(String designation);
	T getOneByIdProduct(long id);
	Collection<T> getAllBySousTotal(double sousTotal);
	Collection<T> getAllByQuantite(long quantite);
	Collection<Item> getAllItemsByIdSale(long id);
}
