package shopmanagementsystem.PaymentManagement.Traite;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import shopmanagementsystem.Connexion;

public class TraiteDaoImpl implements TraiteDao<Traite>{

	Connexion connexion = new Connexion();
	
	@Override
	public Collection<Traite> getAll() {
		String sql = "select * from traite";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			ResultSet res = pst.executeQuery();
			List<Traite> traites = new ArrayList<Traite>();
			while(res.next()) {
				traites.add(new Traite(res.getLong("idTraite"), res.getDate("datePrevue"), res.getDate("dateEffective"), res.getDouble("montant"), res.getString("numCheque"), res.getString("etat"), null));
			}
			return traites;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Traite add(Traite traite) {
		String sql = "insert into traite(dateprevue, dateEffective, montant, numCheque, etat, idSale) values(?, ?, ?, ?, ?, ?)";
		try {
			PreparedStatement pst = connexion.getConnection().prepareStatement(sql);
			pst.setDate(1, traite.getDatePrevue());
			pst.setDate(2, traite.getDateEffective());
			pst.setDouble(3, traite.getMontant());
			pst.setString(4, traite.getNumCheque());
			pst.setString(5, traite.getEtat());
			pst.setLong(6, traite.getSale().getIdSale());
			pst.executeUpdate();
			return traite;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean update(Traite traite, long idTraite) {
		String sql = "update traite set datePrevue = ?, dateEffective = ?, montant = ?, numCheque = ?, etat = ? where idTraite = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setDate(1, traite.getDatePrevue());
			pst.setDate(2, traite.getDateEffective());
			pst.setDouble(3, traite.getMontant());
			pst.setString(4, traite.getNumCheque());
			pst.setString(5, traite.getEtat());
			pst.setLong(6, idTraite);
			pst.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean delete(long id) {
		String sql = "delete from traite where id = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Traite getById(long id) {
		String sql = "select * from traite where idTraite = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, id);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Collection<Traite> getAllByIdSale(long idSale) {
		String sql = "select * from traite where idSale = ?";
		PreparedStatement pst;
		try {
			pst = connexion.getConnection().prepareStatement(sql);
			pst.setLong(1, idSale);
			ResultSet res = pst.executeQuery();
			List<Traite> traites = new ArrayList<Traite>();
			while(res.next()) {
				traites.add(new Traite(res.getLong("idTraite"), res.getDate("datePrevue"), res.getDate("dateEffective"), res.getDouble("montant"), res.getString("numCheque"), res.getString("etat"), null));
			}
			return traites;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
