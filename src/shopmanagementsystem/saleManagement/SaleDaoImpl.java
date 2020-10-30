package shopmanagementsystem.saleManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import shopmanagementsystem.Connexion;
import shopmanagementsystem.ItemManagement.Item;
import shopmanagementsystem.ItemManagement.ItemDaoImpl;
import shopmanagementsystem.customerManagement.Customer;
import shopmanagementsystem.customerManagement.CustomerDaoImpl;
import shopmanagementsystem.productManagement.Product;
import shopmanagementsystem.productManagement.ProductDaoImpl;

public class SaleDaoImpl implements SaleDao<Sale>{

	private Connexion connexion = null;
	private CustomerDaoImpl CDI = new CustomerDaoImpl();
	private ProductDaoImpl PDI = new ProductDaoImpl();
	
	public SaleDaoImpl() {
		connexion = Connexion.getSingleConnexion();
	}
	
	@Override
	public Collection<Sale> getAll() {
		String sql  ="select * from sale";
		try {
			PreparedStatement pst = connexion.getConnection().prepareStatement(sql);
			ResultSet res = pst.executeQuery();
			List<Sale> sales = new ArrayList<Sale>();
			while(res.next()) {
				Customer c = CDI.getOne(res.getLong("idCustomer"));
				List<Item> items = (List<Item>) getAllItemsByIdSale(res.getLong("idSale"));
				sales.add(new Sale(res.getLong("idSale"), res.getDate("dateSale"), res.getDouble("total"), c, items, "Non payee"));
			}
			return sales;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Sale add(Sale sale) {
		String sql = "insert into sale(dateSale, total, idCustomer, etat) values(?, ?, ?, ?)";
		long key = 0;
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setDate(1,sale.getDateSale(), null);
			pst.setDouble(2, sale.getTotal());
			pst.setLong(3, sale.getCustomer().getIdCustomer());
			pst.setString(4, sale.getEtat());
			pst.executeUpdate();
			ResultSet res = pst.getGeneratedKeys();
			if (res.next()) {
				key = res.getInt(1);
				System.out.println("Key Sale " + key);
				sale.setIdSale(key);
					}
			
			return sale;
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean update(Sale sale, long id) {
		String sql = "update sale set dateSale = ?, total = ?, idCustomer = ?, etat = ? where idSale = ?";
		try {
			PreparedStatement pst = connexion.getConnection().prepareStatement(sql);
			pst.setDate(1, (java.sql.Date) sale.getDateSale());
			pst.setDouble(2, sale.getTotal());
			pst.setLong(3, sale.getCustomer().getIdCustomer());
			pst.setString(4, sale.getEtat());
			pst.setLong(5, id);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean delete(long id) {
		String sql = "delete from sale where idSale = ?";
		try {
			PreparedStatement pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, id);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public Boolean deleteByIdSale(long id) {
		String sql = "delete from item where idSale = ?";
		try {
			PreparedStatement pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, id);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Sale getOne(long id) {
		String sql = "select * from sale where idSale = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, id);
			ResultSet res = pst.executeQuery();
			if(res.next()) {
				Customer c = CDI.getOne(res.getLong("idCustomer"));
				List<Item> items = (List<Item>) getAllItemsByIdSale(res.getLong("idSale"));
				return new Sale(res.getLong("idSale"), res.getDate("dateSale"), res.getDouble("total"), c, items, res.getString("etat"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Collection<Sale> getAllByDate(String date) {
		String sql = "select * from sale where dateSale like ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, "%" + date + "%");
			ResultSet res = pst.executeQuery();
			List<Sale> sales =  new ArrayList<Sale>();
			while(res.next()) {
				Customer c = CDI.getOne(res.getLong("idCustomer"));
				List<Item> items = (List<Item>) getAllItemsByIdSale(res.getLong("idSale"));
				sales.add(new Sale(res.getLong("idSale"), res.getDate("dateSale"), res.getDouble("total"),c, items, res.getString("etat")));
			}
			return sales;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<Sale> getAllByTotal(double total) {
		String sql = "select * from sale where total = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setDouble(1, total);
			ResultSet res = pst.executeQuery();
			List<Sale> sales =  new ArrayList<Sale>();
			while(res.next()) {
				Customer c = CDI.getOne(res.getLong("idCustomer"));
				List<Item> items = (List<Item>) getAllItemsByIdSale(res.getLong("idSale"));
				sales.add(new Sale(res.getLong("idSale"), res.getDate("dateSale"), res.getDouble("total"), c, items, res.getString("etat")));
			}
			return sales;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Collection<Sale> getAllByClient(String nom){
		String sql = "select * from sale where idCustomer in (select idCustomer from customer where nom like ?)";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, "%" + nom + "%");
			ResultSet res = pst.executeQuery();
			List<Sale> sales =  new ArrayList<Sale>();
			while(res.next()) {
				Customer c = CDI.getOne(res.getLong("idCustomer"));
				List<Item> items = (List<Item>) getAllItemsByIdSale(res.getLong("idSale"));
				sales.add(new Sale(res.getLong("idSale"), res.getDate("dateSale"), res.getDouble("total"), c, items, res.getString("etat")));
			}
			return sales;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Sale getOneById(long id) {
		String sql = "select * from sale where idSale = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, id);
			ResultSet res = pst.executeQuery();
			if(res.next()) {
				Customer c = CDI.getOne(res.getLong("idCustomer"));
//				List<Item> items = (List<Item>) getAllItemsByIdSale(res.getLong("idSale"));
				return new Sale(res.getLong("idSale"), res.getDate("dateSale"), res.getDouble("total"), c, null, res.getString("etat"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public Collection<Item> getAllItemsByIdSale(long id) {
		String sql = "select * from item where idSale = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, id);
			ResultSet res = pst.executeQuery();
			List<Item> items = new ArrayList<Item>();
			while(res.next()) {
				Product p = PDI.getOne(res.getLong("idProduct"));
				Sale s = getOneById(res.getLong("idSale"));
				items.add(new Item(res.getLong("idItem"), p, res.getLong("quantite"), res.getDouble("sousTotal"), s));
			}
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
