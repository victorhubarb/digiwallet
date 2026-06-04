package digitalwallet;

public class CreditCard extends PaymentMethod {
	private String cardNumber;
	private String expiryDate;
	private String cardType;
	
	public CreditCard(String holderName, String cardNumber, String expiryDate, String cardType) {
		super(holderName);
		this.cardNumber = cardNumber;
		this.expiryDate = expiryDate;
		this.cardType = cardType;
	}
	
	public String getCardNumber() {
		return cardNumber;
	}
	
	public String getExpiryDate() {
		return expiryDate;
	}
	
	public String getCardType() {
		return cardType;
	}
	
	public void processPayment(double amount) {
		System.out.println("Payment of $" + amount + " processed on card ****" + cardNumber.substring(cardNumber.length() - 4));
	}
	
	@Override
	public void linkToAccount() {
		System.out.println("Credit card ****" + cardNumber.substring(cardNumber.length() - 4) + " linked.");
	}
	
	@Override
	public String getSummary() {
		return cardType + " ****" + cardNumber.substring(cardNumber.length() - 4);
	}
	
	@Override
	public String getType() {
		return "CARD";
	}
	
}