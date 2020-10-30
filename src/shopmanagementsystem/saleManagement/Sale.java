package shopmanagementsystem.saleManagement;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;

import shopmanagementsystem.ItemManagement.Item;
import shopmanagementsystem.customerManagement.Customer;

public class Sale {

	private long idSale;
	private Date dateSale;
	private double total;
	private Customer customer;
	private List<Item> items;
	private String etat;
	
	public Sale() {
		
	}

	public Sale(long idSale, Date dateSale, double total, Customer customer, List<Item> items, String etat) {
		super();
		this.idSale = idSale;
		this.dateSale = dateSale;
		this.total = total;
		this.customer = customer;
		this.items = items;
		this.etat = etat;
	}
	
	public Sale(Date dateSale, double total, Customer customer, List<Item> items, String etat) {
		super();
		this.dateSale = dateSale;
		this.total = total;
		this.customer = customer;
		this.items = items;
		this.etat = etat;
	}

	public long getIdSale() {
		return idSale;
	}

	public void setIdSale(long idSale) {
		this.idSale = idSale;
	}

	public Date getDateSale() {
		return dateSale;
	}

	public void setDateSale(Date dateSale) {
		this.dateSale = dateSale;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public List<Item> getItems() {
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public String getNameCustomer() {
		String name = customer.getIdCustomer() + " " + customer.getNom() + " " + customer.getPrenom();
		return name;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}
	
}
