package shopmanagementsystem.customerManagement;

import java.util.Collection;

public interface CustomerDao<T> {

	Boolean add(Customer customer);
	Boolean update(Customer customer, long id);
	Boolean delete(long id);
	
	Collection<T> getAll();
	T getOne(long id);
	Collection<T> getAllByNom(String nom);
	Collection<T> getAllByPrenom(String prenom);
	Collection<T> getAllByEmail(String email);
	Collection<T> getAllByAdresse(String adresse);
}
