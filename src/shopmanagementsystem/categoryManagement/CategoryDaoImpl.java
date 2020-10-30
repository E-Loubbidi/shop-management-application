package shopmanagementsystem.categoryManagement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import shopmanagementsystem.Connexion;

public class CategoryDaoImpl implements CategoryDao<Category>{
	
	private Connexion connexion = null;
	
	public CategoryDaoImpl() {
		 connexion = Connexion.getSingleConnexion();
	}
	
	@Override
	public Collection<Category> getAll() {
		String sql = "select * from category";
		 
		try {
			PreparedStatement pst = connexion.getConnection().prepareStatement(sql);
			ResultSet res = pst.executeQuery();
			List<Category> categories = new ArrayList<Category>();
			while(res.next()) {
				categories.add(new Category(res.getLong("idCategory"), res.getString("intitule")));
			}
			return categories;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection<Category> getAllByIntitule(String intitule) {
		String sql = "select * from category where intitule like ?";
		 
		try {
			PreparedStatement pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, "%" + intitule + "%");
			ResultSet res = pst.executeQuery();
			List<Category> categories = new ArrayList<>();
			while(res.next()) {
				categories.add(new Category(res.getLong("idCategory"), res.getString("intitule")));
			}
			return categories;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public Boolean add(Category category) {
		
		String sql = "insert into category(intitule) values(?)";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, category.getIntitule());
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean update(Category category, long id) {
		String sql = "update category set intitule = ? where idCategory = ?";
		try {
			PreparedStatement pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, category.getIntitule());
			pst.setLong(2, id);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
		
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean delete(long id) {
		
		String sql = "delete from category where idCategory = ?";
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
	public Category getOne(long id) {
		
		String sql = "select * from category where idCategory = ?";
		PreparedStatement pst;
		try {
			
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, id);
			ResultSet res = pst.executeQuery();
			if(res.next()) {
				Category c = new Category(res.getLong("idCategory"), res.getString("intitule"));
				return c;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
		return null;
	}
	
	@Override
	public Category getOneByIntitule(String intitule) {
	
		String sql = "select * from category where intitule like ?";
		PreparedStatement pst;
		try {
			
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setString(1, "%" + intitule + "%");
			ResultSet res = pst.executeQuery();
			if(res.next()) {
				Category c = new Category(res.getLong("idCategory"), res.getString("intitule"));
				return c;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
