package digitalwallet;

import java.util.Random;

public class BankAccount extends PaymentMethod {
	private String routingNumber;
	private String accountNumber;
	private double balance;
	
	public BankAccount(String holderName, String routingNumber, String accountNumber) {
		super(holderName);
		this.routingNumber = routingNumber;
		this.accountNumber = accountNumber;
		
		Random random = new Random();
		this.balance = random.nextInt(10001);
	}
	
	public String getRoutingNumber() {
		return routingNumber;
	}
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	public void debit(double amount) throws InsufficientFundsException {
		if (balance < amount) {
			throw new InsufficientFundsException("Insufficient bank balance.");
		}
		balance -= amount;
		System.out.println("Bank account ****" + accountNumber.substring(accountNumber.length() - 4) + " debited $" + amount);
	}
	
	public void credit(double amount) {
		balance += amount;
		System.out.println("Bank account ****" + accountNumber.substring(accountNumber.length() - 4) + " credited $" + amount);
	}
	
	@Override
	public void linkToAccount() {
		System.out.println("Bank account ****" + accountNumber.substring(accountNumber.length() - 4) + " linked.");
	}
	
	@Override
	public String getSummary() {
		return "Bank account ****" + accountNumber.substring(accountNumber.length() - 4);
	}
	
	@Override
	public String getType() {
		return "BANK";
	}
}
