package shopmanagementsystem.PaymentManagement.Traite;

import java.util.Collection;

public interface TraiteDao<T> {

	Collection<T> getAll();
	T add(T traite);
	boolean update(T traite, long idTraite);
	boolean delete(long id);
	T getById(long id);
}
