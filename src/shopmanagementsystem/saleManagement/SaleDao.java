package shopmanagementsystem.saleManagement;

import java.util.Collection;
import java.util.Date;

public interface SaleDao<T> {

	Collection<T> getAll();
	Sale add(Sale sale);
	Boolean update(Sale sale, long id);
	Boolean delete(long id);
	Boolean deleteByIdSale(long id); 
	T getOne(long id);
	Collection<T> getAllByDate(String date);
	Collection<T> getAllByTotal(double total);
	Collection<T> getAllByClient(String nom);
}
