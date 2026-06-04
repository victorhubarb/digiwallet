package digitalwallet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class App {
	public static void executeMenu(User user) {
		String[] options = {"Send Money",
				"Request Money",
				"Add Money",
				"Withdraw",
				"Transaction History",
				"Link Payment Method",
				"Log Out"
				};
		
		boolean running = true;
		while (running) {
			int choice = JOptionPane.showOptionDialog(null, "Welcome, " + user.getName() + "\nBalance: $" + String.format("%.2f", user.viewBalance()), "DigiWallet", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
			if (choice == 0) {
				menuSendMoney(user);
			} else if (choice == 1) {
				menuRequestMoney(user);
			} else if (choice == 2) {
				menuAddMoney(user);
			} else if (choice == 3) {
				menuWithdraw(user);
			} else if (choice == 4) {
				menuHistory(user);
			} else if (choice == 5) {
				menuLinkPayment(user);
			} else {
				running = false;
			}
		}
	}
	
	public static void menuSendMoney(User user) {
		String email = JOptionPane.showInputDialog(null, "Recipient's email:");
		double amount = readAmount(); // Recursion
		if (amount == -1) {
			return;
		}
		try {
			user.sendMoney(email, amount);
			JOptionPane.showMessageDialog(null, "Money sent successfully!");
		} catch (InsufficientFundsException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error saving transaction");
		}
	}
	
	// Recursion
	public static double readAmount() {
		String input = JOptionPane.showInputDialog(null, "Amount ($):");
		if (input == null) {
			return -1;
		}
		
		try {
			double amount = Double.parseDouble(input);
			if (amount <= 0) {
				JOptionPane.showMessageDialog(null, "Amount must be greater than $0");
				return readAmount();
			}
			return amount;
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "Please enter a valid number");
			return readAmount();
		}
	}
	
	public static void menuRequestMoney(User user) {
		String email = JOptionPane.showInputDialog(null, "Recipient's email:");
		
		double amount = readAmount(); // Recursion
		if (amount == -1) {
			return;
		}
		
		try {
			user.requestMoney(email, amount);
			JOptionPane.showMessageDialog(null, "Request sent successfully!");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error saving transaction");
		}
	}
	
	public static void menuAddMoney(User user) {
	    if (user.getPaymentMethods().isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No payment methods linked.");
	        return;
	    }

	    String[] typeOptions = {"Bank Account", "Credit Card"};
	    int typeChoice = JOptionPane.showOptionDialog(null, "Add money from:", "Add Money", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, typeOptions, null);
	    if (typeChoice == -1) {
	    	return;
	    }

	    ArrayList<PaymentMethod> paymentMethods = user.getPaymentMethods();
	    
	    if (typeChoice == 0) {
	        ArrayList<BankAccount> bankAccounts = new ArrayList<>();
	        for (int i = 0; i < paymentMethods.size(); i++) {
	            if (paymentMethods.get(i).getType().equals("BANK")) {
	            	bankAccounts.add((BankAccount) paymentMethods.get(i));
	            }
	        }
	        if (bankAccounts.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "No bank accounts linked.");
	            return;
	        }
	        String[] options = new String[bankAccounts.size()];
	        for (int i = 0; i < bankAccounts.size(); i++) {
	            options[i] = bankAccounts.get(i).getSummary();
	        }
	        int choice = JOptionPane.showOptionDialog(null, "Select bank account:", "Add Money", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
	        if (choice == -1) return;
	        double amount = readAmount();
	        if (amount == -1) {
	        	return;
	        }
	        try {
	            user.addFundsFromBankAccount(bankAccounts.get(choice), amount);
	            JOptionPane.showMessageDialog(null, "Funds added successfully!");
	        } catch (InsufficientFundsException e) {
	            JOptionPane.showMessageDialog(null, e.getMessage());
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, "Error saving transaction.");
	        }
	    } else {
	        ArrayList<CreditCard> cards = new ArrayList<>();
	        for (int i = 0; i < paymentMethods.size(); i++) {
	            if (paymentMethods.get(i).getType().equals("CARD")) {
	                cards.add((CreditCard) paymentMethods.get(i));
	            }
	        }
	        if (cards.isEmpty()) {
	            JOptionPane.showMessageDialog(null, "No credit cards linked.");
	            return;
	        }
	        String[] options = new String[cards.size()];
	        for (int i = 0; i < cards.size(); i++) {
	            options[i] = cards.get(i).getSummary();
	        }
	        int choice = JOptionPane.showOptionDialog(null, "Select credit card:", "Add Money", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
	        if (choice == -1) {
	        	return;
	        }
	        double amount = readAmount();
	        if (amount == -1) {
	        	return;
	        }
	        try {
	            user.addFundsFromCreditCard(cards.get(choice), amount);
	            JOptionPane.showMessageDialog(null, "Funds added successfully!");
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, "Error saving transaction.");
	        }
	    }
	}
	
	public static void menuWithdraw(User user) {
	    ArrayList<PaymentMethod> paymentMethods = user.getPaymentMethods();
	    ArrayList<BankAccount> bankAccounts = new ArrayList<>();
	    for (int i = 0; i < paymentMethods.size(); i++) {
	        if (paymentMethods.get(i).getType().equals("BANK")) {
	        	bankAccounts.add((BankAccount) paymentMethods.get(i));
	        }
	    }
	    if (bankAccounts.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No bank accounts linked.");
	        return;
	    }
	    String[] options = new String[bankAccounts.size()];
	    for (int i = 0; i < bankAccounts.size(); i++) {
	        options[i] = bankAccounts.get(i).getSummary();
	    }
	    int choice = JOptionPane.showOptionDialog(null, "Select bank account:", "Withdraw", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
	    if (choice == -1) {
	    	return;
	    }
	    double amount = readAmount();
	    if (amount == -1) {
	    	return;
	    }
	    try {
	        user.withdrawFunds(bankAccounts.get(choice), amount);
	        JOptionPane.showMessageDialog(null, "Withdrawal successful!");
	    } catch (InsufficientFundsException e) {
	        JOptionPane.showMessageDialog(null, e.getMessage());
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(null, "Error saving transaction.");
	    }
	}
	
	public static void menuHistory(User user) {
	    ArrayList<TransactionRecord> transactionHistory = user.viewTransactionHistory();
	    if (transactionHistory.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "No transactions yet.");
	        return;
	    }
	    String text = "";
	    for (int i = 0; i < transactionHistory.size(); i++) {
	        text += transactionHistory.get(i).getDetails() + "\n";
	    }
	    JTextArea textArea = new JTextArea(text);
	    textArea.setEditable(false);
	    JScrollPane scrollPane = new JScrollPane(textArea);
	    scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
	    JOptionPane.showMessageDialog(null, scrollPane, "Transaction History", JOptionPane.PLAIN_MESSAGE);
	}
	
	public static void menuLinkPayment(User user) {
	    String[] typeOptions = {"Bank Account", "Credit Card"};
	    int typeChoice = JOptionPane.showOptionDialog(null, "Select payment method type:", "Link Payment Method", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, typeOptions, null);
	    if (typeChoice == -1) {
	    	return;
	    }

	    if (typeChoice == 0) {
	        String holderName = JOptionPane.showInputDialog(null, "Holder name:");
	        if (holderName == null) {
	        	return;
	        }
	        String routing = JOptionPane.showInputDialog(null, "Routing number:");
	        if (routing == null) {
	        	return;
	        }
	        String account = JOptionPane.showInputDialog(null, "Account number:");
	        if (account == null) {
	        	return;
	        }
	        BankAccount bankAccount = new BankAccount(holderName, routing, account);
	        try {
	            user.linkPaymentMethod(bankAccount);
	            JOptionPane.showMessageDialog(null, "Bank account linked successfully!");
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, "Error saving bank account.");
	        }
	    } else {
	        String holderName = JOptionPane.showInputDialog(null, "Holder name:");
	        if (holderName == null) {
	        	return;
	        }
	        String cardNumber = JOptionPane.showInputDialog(null, "Card number:");
	        if (cardNumber == null) {
	        	return;
	        }
	        String expiry = JOptionPane.showInputDialog(null, "Expiry date (MM/YY):");
	        if (expiry == null) {
	        	return;
	        }
	        String[] cardTypes = {"Visa", "Mastercard", "American Express"};
	        int cardTypeChoice = JOptionPane.showOptionDialog(null, "Card type:", "Credit Card", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, cardTypes, null);
	        if (cardTypeChoice == -1) {
	        	return;
	        }
	        CreditCard cc = new CreditCard(holderName, cardNumber, expiry, cardTypes[cardTypeChoice]);
	        try {
	            user.linkPaymentMethod(cc);
	            JOptionPane.showMessageDialog(null, "Credit card linked successfully!");
	        } catch (IOException e) {
	            JOptionPane.showMessageDialog(null, "Error saving credit card.");
	        }
	    }
	}
	
	public static void createAccount() {
		String name = JOptionPane.showInputDialog(null, "Enter your name:");
		String email = JOptionPane.showInputDialog(null, "Enter your email:");
		String phone = JOptionPane.showInputDialog(null, "Enter your phone:");
		String password = JOptionPane.showInputDialog(null, "Enter your password:");
		
		try {			
			FileManager.saveUser(password, name, email, phone, 0);
			String[] options = {"Log In", "Exit"};
			int choice = JOptionPane.showOptionDialog(null,
			        "Account created successfully!",
			        "DigiWallet",
			        JOptionPane.DEFAULT_OPTION,
			        JOptionPane.PLAIN_MESSAGE,
			        null, options, null);
			if (choice == 0) {
			    logIn();
			} else {
			    System.exit(0);
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error creating account");
		}
	}
	
	public static void logIn() {
		String email = JOptionPane.showInputDialog(null, "Email:");
		String password = JOptionPane.showInputDialog(null, "Password:");
		
		try {
			String[] parts = FileManager.logInUser(email, password);
			if (parts == null) {
				JOptionPane.showMessageDialog(null, "Invalid email or password.");
			} else {
				User user = new User(parts[2], parts[0], parts[3]);
				user.setWalletBalance(Double.parseDouble(parts[4]));
				user.setTransactions(FileManager.loadTransactions(email));
				user.setPaymentMethods(FileManager.loadPaymentMethods(email));
				executeMenu(user);
			}
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No accounts registered yet in our systems.");
		}
	}
	
	public static void main(String[] args) {
		String[] options = {"Exit", "Create Account", "Log in"};
		
		boolean running = true;
		while (running) {
			int choice = JOptionPane.showOptionDialog(null, "                                     Welcome to DigiWallet", "DigiWallet", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
			if (choice == 0) {
				running = false;
			} else if (choice == 1) {
				createAccount();
			} else if (choice == 2) {
				logIn();
			}
		}
	}

}
