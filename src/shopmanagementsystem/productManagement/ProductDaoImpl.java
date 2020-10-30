package shopmanagementsystem.productManagement;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.javafx.collections.SetAdapterChange;

import shopmanagementsystem.Connexion;
import shopmanagementsystem.categoryManagement.CategoryDaoImpl;
import shopmanagementsystem.categoryManagement.Category;

public class ProductDaoImpl implements ProductDao<Product>{

	private Connexion connexion = null;
	private CategoryDaoImpl CDI = new CategoryDaoImpl();
	
	public ProductDaoImpl() {
		 connexion = Connexion.getSingleConnexion();
	}
	
	@Override
	public Collection<Product> getAll() {
		String sql = "select * from products";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			ResultSet res = pst.executeQuery();
			List<Product> products = new ArrayList<>();
			while(res.next()) {
				Category c = CDI.getOne(res.getLong("idCategory"));
				products.add(new Product(res.getLong("idProduct"), res.getString("designation"), res.getDouble("prixAchat"), res.getDouble("prixVente"), c));
			}
			return products;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Collection<Product> getAllByDesignation(String designation) {
		String sql = "select * from products where designation like ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, "%" + designation + "%");
			ResultSet res = pst.executeQuery();
			List<Product> products = new ArrayList<>();
			while(res.next()) {
				Category c = CDI.getOne(res.getLong("idCategory"));
				products.add(new Product(res.getLong("idProduct"), res.getString("designation"), res.getDouble("prixAchat"), res.getDouble("prixVente"), c));
			}
			return products;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean add(Product product) {
		String sql = "insert into products(designation,prixAchat,prixVente,idCategory) values(?,?,?,?)";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, product.getDesignation());
			pst.setDouble(2, product.getPrixAchat());
			pst.setDouble(3, product.getPrixVente());
//			Category c = CDI.getOneByIntitule(product.getCategory());
			System.out.println(product.getCategory().getIdCategory());
			pst.setLong(4, product.getCategory().getIdCategory());
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		
		}
		return false;
	}

	@Override
	public Boolean update(Product product, long id) {
		String sql = "update products set designation = ?,prixAchat = ?,prixVente = ?,idCategory = ? where idProduct = ?";
		try {
			PreparedStatement pst = connexion.getConnection().prepareStatement(sql);
			//pst.setLong(1, product.getIdProduct());
			pst.setString(1, product.getDesignation());
			pst.setDouble(2, product.getPrixAchat());
			pst.setDouble(3, product.getPrixVente());
			
			pst.setLong(4, product.getCategory().getIdCategory());
			pst.setLong(5, id);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public Boolean delete(Product product) {
		String sql = "delete from products where idProduct = ?";
		try {
			PreparedStatement pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, product.getIdProduct());
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public Collection<Product> getAllByCategory(Category category) {
		String sql = "select * from products where idCategory = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, category.getIdCategory());
			ResultSet res = pst.executeQuery();
			List<Product> products = new ArrayList<>();
			while(res.next()) {
				products.add(new Product(res.getLong("idProduct"), res.getString("designation"), res.getDouble("prixAchat"), res.getDouble("prixVente"), category));
			}
			return products;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Product getOne(long id) {
		String sql = "select * from products where idProduct = ?";
		try {
			PreparedStatement pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, id);
			ResultSet res = pst.executeQuery();
			Product product = null;
			if(res.next()) {
				Category c = CDI.getOne(res.getLong("idCategory"));
				product = new Product(res.getLong("idProduct"), res.getString("designation"),res.getDouble("prixAchat"),res.getDouble("prixVente"), c);
				}
			return product;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
