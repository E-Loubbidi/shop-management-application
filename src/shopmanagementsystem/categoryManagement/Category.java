package shopmanagementsystem.categoryManagement;

public class Category {
	
	private long idCategory;
	private String intitule;
	
	public Category() {
		
	}

	public Category(long idCategory, String intitule) {
		super();
		this.idCategory = idCategory;
		this.intitule = intitule;
	}

	public Category(String intitule) {
		super();
		this.intitule = intitule;
	}

	public long getIdCategory() {
		return idCategory;
	}

	public void setIdCategory(long idCategory) {
		this.idCategory = idCategory;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}
	
	
}
