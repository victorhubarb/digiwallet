# DigiWallet 💳

![Status](https://img.shields.io/badge/status-completed-green?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-OOP-orange?style=for-the-badge&logo=java)
![Course](https://img.shields.io/badge/CS%20200-Programming%20II-blue?style=for-the-badge)

A Venmo-inspired digital wallet desktop application built in Java with a graphical interface, persistent file-based storage, and full transaction management.

---

## Overview

DigiWallet simulates how a real digital wallet works — users can create an account, link payment methods (bank accounts and credit cards), add funds, send and request money from other users, and withdraw back to a linked bank account. All data persists between sessions through a custom file management system.

The project was developed as the final project for **CS 200 – Programming II** at MassBay Community College.

---

## Features

- **Account creation and authentication** — register with name, email, phone, and password; sessions persist across app restarts
- **Wallet balance management** — real-time balance updates on every transaction
- **Send & request money** — transfer funds to other users by email; request payments with pending status tracking
- **Multiple payment methods** — link bank accounts and credit cards (Visa, Mastercard, Amex); add funds or withdraw back to bank
- **Transaction history** — full log of all transactions with type, amount, counterparty, and status
- **Graphical interface** — built with Java's `JOptionPane` and `JScrollPane` for a dialog-based GUI
- **Data persistence** — flat-file database using `.txt` files per user (accounts, transactions, payment methods)

---

## Architecture & Design

The application follows a clean Object-Oriented design with separation of concerns across 7 classes:

```
digitalwallet/
├── App.java                     # Entry point, menu controller, UI logic
├── User.java                    # Core user entity and business logic
├── PaymentMethod.java           # Abstract base class for payment types
├── BankAccount.java             # Extends PaymentMethod — debit/credit operations
├── CreditCard.java              # Extends PaymentMethod — card processing
├── TransactionRecord.java       # Immutable transaction data model
├── FileManager.java             # Static utility — all file I/O operations
└── InsufficientFundsException.java  # Custom checked exception
```

### Key OOP Concepts Applied

| Concept | Implementation |
|---|---|
| **Abstraction** | `PaymentMethod` abstract class enforces `linkToAccount()`, `getSummary()`, `getType()` contracts |
| **Inheritance** | `BankAccount` and `CreditCard` extend `PaymentMethod` |
| **Polymorphism** | `ArrayList<PaymentMethod>` stores both types; runtime dispatch handles each correctly |
| **Custom Exception** | `InsufficientFundsException` propagates through wallet and bank operations |
| **Recursion** | `readAmount()` re-prompts recursively on invalid input |
| **File I/O** | `FileManager` handles all persistence — per-user flat files as a simplified database |

---

## How to Run

### Prerequisites
- Java 8 or higher
- Any IDE (IntelliJ IDEA, Eclipse, VS Code with Java Extension Pack)

### Steps

```bash
# 1. Clone the repository
git clone https://github.com/victorhubarb/digiwallet.git
cd digiwallet

# 2. Compile all files
javac digitalwallet/*.java

# 3. Run the application
java digitalwallet.App
```

> The application will create `users.txt`, `transactions_<email>.txt`, and `paymentmethods_<email>.txt` files in the working directory to persist data between sessions.

---

## Usage

On launch, the app presents three options:

1. **Create Account** — enter name, email, phone, and password
2. **Log In** — authenticate and resume your session with full history restored
3. **Exit**

Once logged in, the main menu offers:

| Option | Description |
|---|---|
| Send Money | Transfer funds to another user by email |
| Request Money | Send a payment request (logged as PENDING) |
| Add Money | Deposit from a linked bank account or credit card |
| Withdraw | Transfer wallet balance back to a linked bank account |
| Transaction History | Scrollable log of all past transactions |
| Link Payment Method | Add a bank account or credit/debit card |

---

## Design Artifacts

This project was developed with full software engineering documentation:

- **Use Case Diagram** — maps all user interactions and system flows
- **User Personas** — two personas defined to guide UX decisions
- **UML Class Diagram** — complete class relationships and method signatures

---

## What I'd Improve Next

- Replace flat-file storage with SQLite or an embedded database
- Add password hashing (currently stored as plaintext — known limitation)
- Implement actual peer-to-peer transaction resolution (currently send/request are one-sided)
- Add input validation for routing/account numbers and card formats
- Migrate GUI from `JOptionPane` to a proper Swing or JavaFX interface

---

## Author

**Victor Hugo Barbosa**  
CS Student — MassBay Community College  
[GitHub](https://github.com/victorhubarb) · [LinkedIn](https://www.linkedin.com/in/victorhbarbosa/)
