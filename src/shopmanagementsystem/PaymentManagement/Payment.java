package shopmanagementsystem.PaymentManagement;

import java.sql.Date;

import shopmanagementsystem.saleManagement.Sale;

public class Payment {

	private long idPayment;
	private Date datePayment;
	private String typeReglement;
	private double montant;
	private Sale sale;
	
	public Payment() {
		
	}
	
	public Payment(long idPayment, Date datePayment, String typeReglement, double montant, Sale sale) {
		super();
		this.idPayment = idPayment;
		this.datePayment = datePayment;
		this.typeReglement = typeReglement;
		this.montant = montant;
		this.sale = sale;
	}
	
	public Payment(Date datePayment, String typeReglement, double montant, Sale sale) {
		super();
		this.datePayment = datePayment;
		this.typeReglement = typeReglement;
		this.montant = montant;
		this.sale = sale;
	}

	public long getIdPayment() {
		return idPayment;
	}

	public void setIdPayment(long idPayment) {
		this.idPayment = idPayment;
	}

	public Date getDatePayment() {
		return datePayment;
	}

	public void setDatePayment(Date datePayment) {
		this.datePayment = datePayment;
	}

	public String getTypeReglement() {
		return typeReglement;
	}

	public void setTypeReglement(String typeReglement) {
		this.typeReglement = typeReglement;
	}
	
	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}
	
	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}
	
	public long getIdSale() {
		return sale.getIdSale();
	}
}
