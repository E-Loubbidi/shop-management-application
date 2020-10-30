package shopmanagementsystem.ItemManagement;

import shopmanagementsystem.productManagement.Product;
import shopmanagementsystem.saleManagement.Sale;

public class Item {
	
	private long idItem;
	private Product product;
	private long quantite;
	private double sousTotal;
	private Sale sale;
	
	public Item() {
		
	}

	public Item(long idItem, Product product, long quantite, double sousTotal, Sale sale) {
		super();
		this.idItem = idItem;
		this.product = product;
		this.quantite = quantite;
		this.sousTotal = sousTotal;
		this.sale = sale;
	}
	
	public Item(Product product, long quantite, double sousTotal, Sale sale) {
		super();
		this.product = product;
		this.quantite = quantite;
		this.sousTotal = sousTotal;
		this.sale = sale;
	}

	public long getIdItem() {
		return idItem;
	}

	public void setIdItem(long idItem) {
		this.idItem = idItem;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public long getQuantite() {
		return quantite;
	}

	public void setQuantite(long quantite) {
		this.quantite = quantite;
	}

	public double getSousTotal() {
		return sousTotal;
	}

	public void setSousTotal(double sousTotal) {
		this.sousTotal = sousTotal;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}
	
	public String getDesignationProduct() {
		String designationProduct = product.getIdProduct() + " " + product.getDesignation();
		return designationProduct;
	}
	
	public double getPrixVenteProduct() {
		return product.getPrixVente();
	}
}
