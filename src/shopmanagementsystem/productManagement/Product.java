package shopmanagementsystem.productManagement;

import shopmanagementsystem.categoryManagement.Category;

public class Product {

	private long idProduct;
	private String designation;
	private double prixAchat;
	private double prixVente;
	private Category category;
	public Product() {
		
	}
	public Product(long idProduct, String designation, double prixAchat, double prixVente, Category category) {
		super();
		this.idProduct = idProduct;
		this.designation = designation;
		this.prixAchat = prixAchat;
		this.prixVente = prixVente;
		this.category = category;
	}
	public Product(String designation, double prixAchat, double prixVente, Category category) {
		super();
		this.designation = designation;
		this.prixAchat = prixAchat;
		this.prixVente = prixVente;
		this.category = category;
	}
	public long getIdProduct() {
		return idProduct;
	}
	public void setIdProduct(long idProduct) {
		this.idProduct = idProduct;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public double getPrixAchat() {
		return prixAchat;
	}
	public void setPrixAchat(double prixAchat) {
		this.prixAchat = prixAchat;
	}
	public double getPrixVente() {
		return prixVente;
	}
	public void setPrixVente(double prixVente) {
		this.prixVente = prixVente;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String getCategoryIntitule() {
		return category.getIntitule();
	}
}
