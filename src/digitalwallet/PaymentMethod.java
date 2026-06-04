package digitalwallet;

// Abstract class to make sure it's impossible to create a generic PaymentMethod instance
public abstract class PaymentMethod {
	private String holderName;
	
	public PaymentMethod(String holderName) {
		this.holderName = holderName;
	}
	
	// GETTERS
	public String getHolderName() {
		return holderName;
	}
	
	// METHOD abstract to obligate any subclass to create and override linkToAccount method
	public abstract void linkToAccount();
	public abstract String getSummary();
	public abstract String getType();
	
}
