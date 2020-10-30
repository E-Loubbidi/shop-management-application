package shopmanagementsystem.PaymentManagement.Traite;

import java.sql.Date;

import shopmanagementsystem.saleManagement.Sale;

public class Traite {

	private long idTraite;
	private Date datePrevue;
	private Date dateEffective;
	private double montant;
	private String numCheque;
	private String etat;
	private Sale sale;
	
	public Traite() {
		
	}

	public Traite(long idTraite, Date date, Date dateEffective, double montant, String numCheque, String etat, Sale sale) {
		super();
		this.idTraite = idTraite;
		this.datePrevue = date;
		this.dateEffective = dateEffective;
		this.montant = montant;
		this.numCheque = numCheque;
		this.etat = etat;
		this.sale = sale;
	}

	public Traite(Date datePrevue, Date dateEffective, double montant, String numCheque, String etat, Sale sale) {
		super();
		this.datePrevue = datePrevue;
		this.dateEffective = dateEffective;
		this.montant = montant;
		this.numCheque = numCheque;
		this.etat = etat;
		this.sale = sale;
	}

	public long getIdTraite() {
		return idTraite;
	}

	public void setIdTraite(long idTraite) {
		this.idTraite = idTraite;
	}

	public Date getDatePrevue() {
		return datePrevue;
	}

	public void setDatePrevue(Date datePrevue) {
		this.datePrevue = datePrevue;
	}

	public Date getDateEffective() {
		return dateEffective;
	}

	public void setDateEffective(Date dateEffective) {
		this.dateEffective = dateEffective;
	}

	public double getMontant() {
		return montant;
	}

	public void setMontant(double montant) {
		this.montant = montant;
	}

	public String getNumCheque() {
		return numCheque;
	}

	public void setNumCheque(String numCheque) {
		this.numCheque = numCheque;
	}

	public String getEtat() {
		return etat;
	}

	public void setEtat(String etat) {
		this.etat = etat;
	}

	public Sale getSale() {
		return sale;
	}

	public void setSale(Sale sale) {
		this.sale = sale;
	}
	
	
}
