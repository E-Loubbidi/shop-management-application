package shopmanagementsystem.PaymentManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import shopmanagementsystem.Connexion;
import shopmanagementsystem.ItemManagement.Item;
import shopmanagementsystem.customerManagement.Customer;
import shopmanagementsystem.customerManagement.CustomerDaoImpl;
import shopmanagementsystem.saleManagement.Sale;
import shopmanagementsystem.saleManagement.SaleDaoImpl;

public class PaymentDaoImpl implements PaymentDao<Payment>{

	private Connexion connexion = null;
	private SaleDaoImpl SDI = new SaleDaoImpl();
	private CustomerDaoImpl CDI = new CustomerDaoImpl();
	
	public PaymentDaoImpl() {
		connexion = Connexion.getSingleConnexion();
	}
	
	@Override
	public Collection<Payment> getAllPayement() {
		String sql = "select * from payment";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			ResultSet res = pst.executeQuery();
			List<Payment> payments = new ArrayList<Payment>();
			while(res.next()) {
				Sale sale = SDI.getOne(res.getLong("idSale"));
				payments.add(new Payment(res.getLong("idPayment"), res.getDate("datePayment"), res.getString("typeReglement"), res.getDouble("montant"), sale));
			}
			return payments;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean add(Payment payement) {
		String sql = "insert into payment(datePayment, typeReglement, montant, idSale) value(?, ?, ?, ?)";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setDate(1, payement.getDatePayment());
			pst.setString(2, payement.getTypeReglement());
			pst.setDouble(3, payement.getMontant());
			pst.setDouble(4, payement.getSale().getIdSale());
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean update(Payment payement, long id) {
		String sql = "update payment set datePayment = ?, typeReglement = ?, montant = ? where id = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setDate(1, payement.getDatePayment());
			pst.setString(2, payement.getTypeReglement());
			pst.setDouble(3, payement.getMontant());
			pst.setLong(4, id);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean delete(long id) {
		String sql = "delete from payment where idPayment = ?";
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
	public Payment getOne(long id) {
		String sql = "select * from payment where idPayment = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, id);
			ResultSet res = pst.executeQuery();
			if(res.next()) {
				Sale sale = SDI.getOne(res.getLong("idSale"));
				return new Payment(res.getLong("idPayment"), res.getDate("datePayment"), res.getString("typeReglement"), res.getDouble("montant"), sale);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Sale> getAllSalesNotPayed(String etat){
		String sql = "select * from sale where etat like ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, etat);
			ResultSet res = pst.executeQuery();
			List<Sale> sales = new ArrayList<Sale>();
			while(res.next()) {
				Customer c = CDI.getOne(res.getLong("idCustomer"));
				List<Item> items = (List<Item>) SDI.getAllItemsByIdSale(res.getLong("idSale"));
				sales.add(new Sale(res.getLong("idSale"), res.getDate("dateSale"), res.getDouble("total"), c, items, res.getString("etat")));
			}
			return sales;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Collection<Payment> getAllByDate(String date) {
		String sql = "select * from payment where datePayment like ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, "%" + date + "%");
			ResultSet res = pst.executeQuery();
			List<Payment> payments = new ArrayList<Payment>();
			while(res.next()) {
				Sale sale = SDI.getOne(res.getLong("idSale"));
				payments.add(new Payment(res.getLong("idPayment"), res.getDate("datePayment"), res.getString("typeReglement"), res.getDouble("montant"), sale));
			}
			return payments;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Collection<Payment> getAllByType(String type) {
		String sql = "select * from payment where typeReglement like ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, "%" + type + "%");
			ResultSet res = pst.executeQuery();
			List<Payment> payments = new ArrayList<Payment>();
			while(res.next()) {
				Sale sale = SDI.getOne(res.getLong("idSale"));
				payments.add(new Payment(res.getLong("idPayment"), res.getDate("datePayment"), res.getString("typeReglement"), res.getDouble("montant"), sale));
			}
			return payments;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
