package shopmanagementsystem.ItemManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import shopmanagementsystem.Connexion;
import shopmanagementsystem.productManagement.Product;
import shopmanagementsystem.productManagement.ProductDaoImpl;
import shopmanagementsystem.saleManagement.Sale;
import shopmanagementsystem.saleManagement.SaleDaoImpl;

public class ItemDaoImpl implements ItemDao<Item>{

	private Connexion connexion = Connexion.getSingleConnexion();
	private ProductDaoImpl PDI = new ProductDaoImpl();
	private SaleDaoImpl SDI = new SaleDaoImpl();
	
	@Override
	public Collection<Item> getAll() {
		String sql = "select * from item";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			ResultSet res = pst.executeQuery();
			List<Item> items = new ArrayList<Item>();
			while(res.next()) {
				Product p = PDI.getOne(res.getLong("idProduct"));
				Sale s = SDI.getOne(res.getLong("idSale"));
				items.add(new Item(res.getLong("idItem"), p, res.getLong("quantite"), res.getDouble("sousTotal"), s));
			}
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean add(Item item) {
		String sql = "insert into item(idProduct, quantite, sousTotal, idSale) value(?, ?, ?, ?)";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, item.getProduct().getIdProduct());
			pst.setLong(2, item.getQuantite());
			pst.setDouble(3, item.getSousTotal());
			pst.setLong(4, item.getSale().getIdSale());
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}
	
	@Override
	public Boolean addItem(Item item) {
		String sql = "insert into item(idItem, idProduct, quantite, sousTotal, idSale) value(?, ?, ?, ?, ?)";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, item.getIdItem());
			pst.setLong(2, item.getProduct().getIdProduct());
			pst.setLong(3, item.getQuantite());
			pst.setDouble(4, item.getSousTotal());
			pst.setLong(5, item.getSale().getIdSale());
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public Boolean update(Item item, long id) {
		String sql = "update item set idIem = ?, idProduct = ?, quantite = ?, sousToatl = ?, idSale = ? where idItem = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, item.getIdItem());
			pst.setLong(2, item.getProduct().getIdProduct());
			pst.setLong(3, item.getQuantite());
			pst.setDouble(4, item.getSousTotal());
			pst.setLong(5, item.getSale().getIdSale());
			pst.setLong(6, id);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public Boolean delete(long id) {
		String sql = "delete from item where idItem = ?";
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
	public Boolean deleteAllByIdSale(long id) {
		String sql = "delete from item where idSale = ?";
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
	public Item getOne(long id) {
		String sql = "select * from item where idItem = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, id);
			ResultSet res = pst.executeQuery();
			Item item = null;
			if(res.next()) {
				Product p = PDI.getOne(res.getLong("idProduct"));
				Sale s = SDI.getOne(res.getLong("idSale"));
				item = new Item(res.getLong("idItem"), p, res.getLong("quantite"), res.getDouble("sousTotal"), s);
			}
			return item;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Item getOneByDesignation(String designation) {
		String sql = "select * from item where idProduct in (select idProduct from products where designation like ?)";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, "%" + designation + "%");
			ResultSet res = pst.executeQuery();
			Item item = null;
			if(res.next()) {
				Product p = PDI.getOne(res.getLong("idProduct"));
				Sale s = SDI.getOne(res.getLong("idSale"));
				item = new Item(res.getLong("idItem"), p, res.getLong("quantite"), res.getDouble("sousTotal"), s);
			}
			return item;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Item getOneByIdProduct(long id) {
		String sql = "select * from item where idProduct = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, id);
			ResultSet res = pst.executeQuery();
			Item item = null;
			if(res.next()) {
				Product p = PDI.getOne(res.getLong("idProduct"));
				Sale s = SDI.getOne(res.getLong("idSale"));
				item = new Item(res.getLong("idItem"), p, res.getLong("quantite"), res.getDouble("sousTotal"), s);
			}
			return item;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<Item> getAllBySousTotal(double sousTotal) {
		String sql = "select * from item where sousTotal = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setDouble(1, sousTotal);
			ResultSet res = pst.executeQuery();
			List<Item> items = new ArrayList<Item>();
			while(res.next()) {
				Product p = PDI.getOne(res.getLong("idProduct"));
				Sale s = SDI.getOne(res.getLong("idSale"));
				items.add(new Item(res.getLong("idItem"), p, res.getLong("quantite"), res.getDouble("sousTotal"), s));
			}
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<Item> getAllByQuantite(long quantite) {
		String sql = "select * from item where quantite = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, quantite);
			ResultSet res = pst.executeQuery();
			List<Item> items = new ArrayList<Item>();
			while(res.next()) {
				Product p = PDI.getOne(res.getLong("idProduct"));
				Sale s = SDI.getOne(res.getLong("idSale"));
				items.add(new Item(res.getLong("idItem"), p, res.getLong("quantite"), res.getDouble("sousTotal"), s));
			}
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
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
				Sale s = SDI.getOne(res.getLong("idSale"));
				items.add(new Item(res.getLong("idItem"), p, res.getLong("quantite"), res.getDouble("sousTotal"), s));
			}
			return items;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
