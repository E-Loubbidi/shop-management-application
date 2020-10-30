package shopmanagementsystem.PaymentManagement;

import java.util.Collection;

public interface PaymentDao<T>{

	Collection<T> getAllPayement();
	Boolean add(T payement);
	Boolean update(T payement, long id);
	Boolean delete(long id);
	T getOne(long id);
	Collection<T> getAllByDate(String date);
	Collection<T> getAllByType(String type);
}
