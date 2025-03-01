package src;


public class Motorcycle {
	
	private String date;
    private String stockLabel;
    private String brand;
    private String engineNumber;
    private String purchaseStatus;

    
    
    // Constructor, getters, setters 
	public Motorcycle(String date, String stockLabel, String brand, String engineNumber, String purchaseStatus) {
		this.date = date;
		this.stockLabel = stockLabel;
		this.brand = brand;
		this.engineNumber = engineNumber;
		this.purchaseStatus = purchaseStatus;
	}
	
	@Override
    public String toString() {
        return " ||     " + date +"    |       "+ stockLabel +"       |      " + brand +"     |          "+ engineNumber +"         |          "+ purchaseStatus +"       || ";
        }
	
	
	public String getDate() {
		return date;
	}
	public String getStockLabel() {
		return stockLabel;
	}
	public String getBrand() {
		return brand;
	}
	public String getEngineNumber() {
		return engineNumber;
	}
	public String getPurchaseStatus() {
		return purchaseStatus;
	}
	
	
	
	public void setDate(String date) {
		this.date = date;
	}
	public void setStockLabel(String stockLabel) {
		this.stockLabel = stockLabel;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public void setEngineNumber(String engineNumber) {
		this.engineNumber = engineNumber;
	}
	public void setPurchaseStatus(String purchaseStatus) {
		this.purchaseStatus = purchaseStatus;
	}	   

}
