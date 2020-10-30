package shopmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {

	private static Connexion singleConnexion = null;
	private Connection connection;
	public Connexion() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		String url = "jdbc:mysql://localhost:3306/db_shopmanagement";
		try {
			connection = DriverManager.getConnection(url ,"root", "");
			System.out.println("Connecté à la bdd...");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public static Connexion getSingleConnexion() {
		if(singleConnexion == null) {
			singleConnexion = new Connexion();
		}
		return singleConnexion;
	}
	public Connection getConnection() {
		return connection;
	}
}
