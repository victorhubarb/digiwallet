package digitalwallet;
import java.io.IOException;
import java.util.ArrayList;

public class User {
	private String name;
	private String email;
	private String phone;
	private double walletBalance;
	private int transactionCounter = 1;
	private ArrayList<TransactionRecord> transactions = new ArrayList<>();
	private ArrayList<PaymentMethod> paymentMethods = new ArrayList<>();
	
	public User(String name, String email, String phone) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.walletBalance = 0;
		this.transactions = new ArrayList<>();
		this.paymentMethods = new ArrayList<>();
	}
	
	// Getters
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setTransactions(ArrayList<TransactionRecord> transactions) {
		this.transactions = transactions;
		this.transactionCounter = transactions.size() + 1;
	}
	
	public void setPaymentMethods(ArrayList<PaymentMethod> paymentMethods) {
		this.paymentMethods = paymentMethods;
	}
	
	public double viewBalance() {
		return walletBalance;
	}
	
	// Setters
	public void setWalletBalance(double amount) {
		walletBalance = amount;
	}
	
	public ArrayList<TransactionRecord> viewTransactionHistory() {
		return transactions;
	}
	
	public ArrayList<PaymentMethod> getPaymentMethods() {
		return paymentMethods;
	}
	
	public void addFundsFromBankAccount(BankAccount bankAccount, double amount) throws InsufficientFundsException, IOException {
		bankAccount.debit(amount);
		walletBalance += amount;
		TransactionRecord transaction = new TransactionRecord("T" + transactionCounter++, amount, "DEPOSIT", bankAccount.getSummary());
		transactions.add(transaction);
		FileManager.saveTransaction(email, transaction.getTransactionId(), transaction.getAmount(), transaction.getStatus(), transaction.getType(), transaction.getCounterparty());
	    FileManager.updateBalance(email, walletBalance);
	}
	
	public void addFundsFromCreditCard(CreditCard creditCard, double amount) throws IOException {
		creditCard.processPayment(amount);
		walletBalance += amount;
		TransactionRecord transaction = new TransactionRecord("T" + transactionCounter++, amount, "DEPOSIT", creditCard.getSummary());
		transactions.add(transaction);
		FileManager.saveTransaction(email, transaction.getTransactionId(), transaction.getAmount(), transaction.getStatus(), transaction.getType(), transaction.getCounterparty());
	    FileManager.updateBalance(email, walletBalance);
	}
	
	public void sendMoney(String counterparty, double amount) throws InsufficientFundsException, IOException {
		if (walletBalance < amount) {
			throw new InsufficientFundsException("Insufficient wallet balance");
		}
		
		walletBalance -= amount;
		TransactionRecord transaction = new TransactionRecord("T" + transactionCounter++, amount, "SEND", counterparty);
		transactions.add(transaction);
		FileManager.saveTransaction(email, transaction.getTransactionId(), transaction.getAmount(), transaction.getStatus(), transaction.getType(), transaction.getCounterparty());
	    FileManager.updateBalance(email, walletBalance);
	}
	
	public void requestMoney(String counterparty, double amount) throws IOException {
		TransactionRecord transaction = new TransactionRecord("T" + transactionCounter++, amount, "REQUEST", counterparty);
		transactions.add(transaction);
		FileManager.saveTransaction(email, transaction.getTransactionId(), transaction.getAmount(), transaction.getStatus(), transaction.getType(), transaction.getCounterparty());
	}
	
	public void withdrawFunds(BankAccount bankAccount, double amount) throws InsufficientFundsException, IOException {
		if (walletBalance < amount) {
			throw new InsufficientFundsException("Insufficient wallet balance");
		}
		
		walletBalance -= amount;
		bankAccount.credit(amount);
		TransactionRecord transaction = new TransactionRecord("T" + transactionCounter++, amount, "WITHDRAW", bankAccount.getSummary());
		transactions.add(transaction);
		FileManager.saveTransaction(email, transaction.getTransactionId(), transaction.getAmount(), transaction.getStatus(), transaction.getType(), transaction.getCounterparty());
	    FileManager.updateBalance(email, walletBalance);
	}
	
	public void linkPaymentMethod(PaymentMethod paymentMethod) throws IOException {
		for (int i = 0; i < paymentMethods.size(); i++) {
			if (paymentMethods.get(i).getSummary().equals(paymentMethod.getSummary())) {
				System.out.println("Payment method already linked.");
				return;
			}
		}
		paymentMethods.add(paymentMethod);
		paymentMethod.linkToAccount();
		
		try {
		    if (paymentMethod.getType().equals("BANK")) {
		        FileManager.saveBankAccount(email, (BankAccount) paymentMethod);
		    } else {
		        FileManager.saveCreditCard(email, (CreditCard) paymentMethod);
		    }
		} catch (IOException e) {
		    System.out.println("Error saving payment method.");
		}
	}

}
