package shopmanagementsystem.customerManagement;

public class Customer {

	private long idCustomer;
	private String nom;
	private String prenom;
	private String email;
	private String adresse;
	
	public Customer() {
		
	}
	
	public Customer(long idCustomer, String nom, String prenom, String email, String adresse) {
		super();
		this.idCustomer = idCustomer;
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.email = email;
	}
	
	public Customer(String nom, String prenom, String email, String adresse) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adresse = adresse;
		this.email = email;
	}

	public long getIdCustomer() {
		return idCustomer;
	}

	public void setIdCustomer(long idCustomer) {
		this.idCustomer = idCustomer;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
	
	
}
