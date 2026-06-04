package digitalwallet;

public class TransactionRecord {
	private String transactionId;
	private double amount;
	private String status;
	private String type;
	private String counterparty;
	
	public TransactionRecord(String transactionId, double amount, String type, String counterparty) {
		this.transactionId = transactionId;
		this.amount = amount;
		this.type = type;
		this.counterparty = counterparty;
		if (type.equals("REQUEST")) {
			this.status = "PENDING";
		} else {
			this.status = "COMPLETED";
		}
	}
	
	// GETTERS
	public String getTransactionId() {
		return transactionId;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getType() {
		return type;
	}
	
	public String getCounterparty() {
		return counterparty;
	}
	
	// METHODS
	public String getDetails() {
		String formattedCounterparty;
		if (type.equals("REQUEST") || type.equals("DEPOSIT")) {
			formattedCounterparty = "From: " + counterparty;
		} else {
			formattedCounterparty = "To: " + counterparty;
		}
		
		return transactionId + " | " + type + " | " + String.format("$%.2f", amount) + " | " + formattedCounterparty + " | " + " | " + status; 
	}
}
