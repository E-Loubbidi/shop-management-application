package shopmanagementsystem.customerManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import shopmanagementsystem.Connexion;

public class CustomerDaoImpl implements CustomerDao<Customer>{

	private Connexion connexion = null;
	
	public CustomerDaoImpl() {
		connexion = Connexion.getSingleConnexion();
	}
	
	@Override
	public Boolean add(Customer customer) {
		
		String sql = "insert into customer(nom, prenom, email, adresse) values(?, ?, ?, ?)";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, customer.getNom());
			pst.setString(2, customer.getPrenom());
			pst.setString(3, customer.getEmail());
			pst.setString(4, customer.getAdresse());
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean update(Customer customer, long id) {
		String sql = "update customer set nom = ?, prenom = ?, email = ?, adresse = ? where idCustomer = ?";
		try {
			PreparedStatement pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, customer.getNom());
			pst.setString(2, customer.getPrenom());
			pst.setString(3, customer.getEmail());
			pst.setString(4, customer.getAdresse());
			pst.setLong(5, id);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean delete(long id) {
		String sql = "delete from customer where idCustomer = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, id);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public Collection<Customer> getAll() {
		String sql = "select * from customer";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			ResultSet res = pst.executeQuery();
			List<Customer> customers = new ArrayList<Customer>();
			while(res.next()) {
				customers.add(new Customer(res.getLong("idCustomer"), res.getString("nom"), res.getString("prenom"), res.getString("email"), res.getString("adresse")));
			}
			return customers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Customer getOne(long id) {
		String sql = "select * from customer where idCustomer = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, id);
			ResultSet res = pst.executeQuery();
			if(res.next()) {
				return new Customer(res.getLong("idCustomer"), res.getString("nom"), res.getString("prenom"), res.getString("email"),res.getString("adresse"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<Customer> getAllByNom(String nom) {
		String sql = "select * from customer where nom like ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, "%" + nom + "%");
			ResultSet res = pst.executeQuery();
			List<Customer> customers = new ArrayList<Customer>();
			while(res.next()) {
				customers.add(new Customer(res.getLong("idCustomer"), res.getString("nom"), res.getString("prenom"), res.getString("email"), res.getString("adresse")));
			}
			return customers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<Customer> getAllByPrenom(String prenom) {
		String sql = "select * from customer where prenom like ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, "%" + prenom + "%");
			ResultSet res = pst.executeQuery();
			List<Customer> customers = new ArrayList<Customer>();
			while(res.next()) {
				customers.add(new Customer(res.getLong("idCustomer"), res.getString("nom"), res.getString("prenom"), res.getString("email"), res.getString("adresse")));
			}
			return customers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<Customer> getAllByEmail(String email) {
		String sql = "select * from customer where email like ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, "%" + email + "%");
			ResultSet res = pst.executeQuery();
			List<Customer> customers = new ArrayList<Customer>();
			while(res.next()) {
				customers.add(new Customer(res.getLong("idCustomer"), res.getString("nom"), res.getString("prenom"), res.getString("email"), res.getString("adresse")));
			}
			return customers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<Customer> getAllByAdresse(String adresse) {
		String sql = "select * from customer where adresse like ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, "%" + adresse + "%");
			ResultSet res = pst.executeQuery();
			List<Customer> customers = new ArrayList<Customer>();
			while(res.next()) {
				customers.add(new Customer(res.getLong("idCustomer"), res.getString("nom"), res.getString("prenom"), res.getString("email"), res.getString("adresse")));
			}
			return customers;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
