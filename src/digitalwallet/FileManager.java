package digitalwallet;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class FileManager {
	public static void saveUser(String password, String name, String email, String phone, double walletBalance) throws IOException {
		PrintWriter writer = new PrintWriter(new FileWriter("users.txt", true));
		writer.println(email + "|" + password + "|" + name + "|" + phone + "|" + walletBalance);
		writer.close();
	}
	
	public static void saveTransaction(String email, String transactionId, double amount, String status, String type, String counterparty) throws IOException {
		PrintWriter writer = new PrintWriter(new FileWriter("transactions_" + email + ".txt", true));
		writer.println(transactionId + "|" + amount + "|" + status + "|" + type + "|" + counterparty);
		writer.close();
	}
	
	public static void saveBankAccount(String email, BankAccount bankAccount) throws IOException {
		PrintWriter writer = new PrintWriter(new FileWriter("paymentmethods_" + email + ".txt", true));
		writer.println("BANK|" + bankAccount.getHolderName() + "|" + bankAccount.getRoutingNumber() + "|" + bankAccount.getAccountNumber());
		writer.close();
	}
	
	public static void saveCreditCard(String email, CreditCard creditCard) throws IOException {
		PrintWriter writer = new PrintWriter(new FileWriter("paymentmethods_" + email + ".txt", true));
		writer.println("CARD|" + creditCard.getHolderName() + "|" + creditCard.getCardNumber() + "|" + creditCard.getExpiryDate() + "|" + creditCard.getCardType());
		writer.close();
	}
	
	public static String[] logInUser(String email, String password) throws FileNotFoundException {
		File file = new File("users.txt");
		Scanner inputFile = new Scanner(file);
		
		while (inputFile.hasNextLine()) {
			String line = inputFile.nextLine();
			String[] parts = line.split("\\|");
			if (parts[0].equals(email) && parts[1].equals(password)) {
				inputFile.close();
				return parts;
			}
		}
		inputFile.close();
		return null;
	}
	
	public static ArrayList<TransactionRecord> loadTransactions(String email) {
		ArrayList<TransactionRecord> transactions = new ArrayList<>();
		File file = new File("transactions_" + email + ".txt");
		try {			
			Scanner inputFile = new Scanner(file);
			while (inputFile.hasNextLine()) {
				String line = inputFile.nextLine();
				String[] parts = line.split("\\|");
				TransactionRecord transaction = new TransactionRecord(parts[0], Double.parseDouble(parts[1]), parts[3], parts[4]);
				transactions.add(transaction);
			}
			inputFile.close();
		} catch (FileNotFoundException e) {
			
		}
		return transactions;
	}
	
	public static ArrayList<PaymentMethod> loadPaymentMethods(String email) {
		ArrayList<PaymentMethod> list = new ArrayList<>();
		File file = new File("paymentmethods_" + email + ".txt");
		try {			
			Scanner inputFile = new Scanner(file);
			while (inputFile.hasNextLine()) {
				String[] parts = inputFile.nextLine().split("\\|");
				if (parts[0].equals("BANK")) {
					list.add(new BankAccount(parts[1], parts[2], parts[3]));
				} else if (parts[0].equals("CARD")) {
					list.add(new CreditCard(parts[1], parts[2], parts[3], parts[4]));
				}
			}
			inputFile.close();
		} catch (FileNotFoundException e) {
			
		}
		return list;
	}
	
	public static void updateBalance(String email, double newBalance) throws IOException {
		File file = new File("users.txt");
		Scanner inputFile = new Scanner(file);
		ArrayList<String> lines = new ArrayList<>();
		while (inputFile.hasNextLine()) {
			String line = inputFile.nextLine();
			String[] parts = line.split("\\|");
			if (parts[0].equals(email)) {
				lines.add(parts[0] + "|" + parts[1] + "|" + parts[2] + "|" + parts[3] + "|" + String.format("%.2f", newBalance));
			} else {
				lines.add(line);
			}
		}
		inputFile.close();
		PrintWriter writer = new PrintWriter(new FileWriter("users.txt", false));
		for (int i = 0; i < lines.size(); i++) {
			writer.println(lines.get(i));
		}
		writer.close();
	}
}
