# DigiWallet 💳

![Status](https://img.shields.io/badge/status-completed-green?style=for-the-badge)
![Java](https://img.shields.io/badge/Java-OOP-orange?style=for-the-badge&logo=java)
![Course](https://img.shields.io/badge/CS%20200-Programming%20II-blue?style=for-the-badge)

A Venmo-inspired digital wallet built in Java — with a GUI, persistent storage, and real transaction management.

---

## Overview

I started this project with the idea of building a bank system, but somewhere along the way it turned into something closer to Venmo. Users can create an account, link payment methods, add funds, send and request money from others, and withdraw back to a linked bank account. Everything persists between sessions — close the app, reopen it, and your balance, transaction history, and linked cards are all exactly where you left them.

This was my final project for **CS 200 – Programming II** at MassBay Community College. Honestly, it ended up more extensive than I originally planned — I kept wanting to improve it as I built it.

---

## Features

- **Account creation and authentication** — register with name, email, phone, and password; log back in anytime and pick up right where you left off
- **Wallet balance** — updates in real time with every transaction
- **Send & request money** — transfer funds to other users by email; requests are tracked as PENDING until resolved
- **Multiple payment methods** — link bank accounts and credit cards (Visa, Mastercard, Amex); use them to add funds or withdraw
- **Transaction history** — scrollable log with type, amount, counterparty, and status for every transaction
- **Graphical interface** — dialog-based GUI built with Java's `JOptionPane` and `JScrollPane`
- **Data persistence** — flat-file storage using per-user `.txt` files as a simplified database

---

## Architecture & Design

The app is structured around 7 classes, each with a clear responsibility:

```
digitalwallet/
├── App.java                        # Entry point, menu controller, all UI logic
├── User.java                       # Core user entity and business logic
├── PaymentMethod.java              # Abstract base class for payment types
├── BankAccount.java                # Extends PaymentMethod — debit/credit operations
├── CreditCard.java                 # Extends PaymentMethod — card processing
├── TransactionRecord.java          # Transaction data model
├── FileManager.java                # Static utility — all file I/O
└── InsufficientFundsException.java # Custom checked exception
```

### OOP Concepts Applied

| Concept | How it shows up |
|---|---|
| **Abstraction** | `PaymentMethod` is abstract — impossible to instantiate directly; forces `BankAccount` and `CreditCard` to implement `linkToAccount()`, `getSummary()`, and `getType()` |
| **Inheritance** | Both `BankAccount` and `CreditCard` extend `PaymentMethod` |
| **Polymorphism** | `ArrayList<PaymentMethod>` holds both types; the right behavior is dispatched at runtime |
| **Custom Exception** | `InsufficientFundsException` is thrown and caught across wallet and bank operations |
| **Recursion** | `readAmount()` calls itself when the input is invalid — keeps prompting until it gets a real number |
| **File I/O** | `FileManager` handles all reads and writes — one file per user for accounts, transactions, and payment methods |

---

## How to Run

**Prerequisites:** Java 8 or higher + any IDE (IntelliJ IDEA, Eclipse, or VS Code with the Java Extension Pack)

```bash
# Clone the repo
git clone https://github.com/victorhubarb/digiwallet.git
cd digiwallet

# Compile
javac src/digitalwallet/*.java -d out

# Run
java -cp out digitalwallet.App
```

> On first run, the app creates `users.txt`, `transactions_<email>.txt`, and `paymentmethods_<email>.txt` in the working directory. These files are what keeps your session alive between runs.

---

## Usage

When you open the app, you get three options: create an account, log in, or exit. Once you're in, the main menu looks like this:

| Option | What it does |
|---|---|
| Send Money | Transfer funds to another user by email |
| Request Money | Send a payment request — logged as PENDING |
| Add Money | Deposit from a linked bank account or credit card |
| Withdraw | Move wallet balance back to a linked bank account |
| Transaction History | Full scrollable log of every transaction |
| Link Payment Method | Add a bank account or credit/debit card |

---

## Design Artifacts

The project includes full software engineering documentation — not just code:

- **[UML Class Diagram](docs/uml-class-diagram.pdf)** — class relationships and method signatures
- **[Use Case Diagram](docs/use-case-diagram.pdf)** — all user interactions and system flows
- **[Use Cases](docs/use-cases.pdf)** — detailed descriptions of each user action
- **[User Personas](docs/personas.pdf)** — two personas that guided design decisions
- **[Project Summary](docs/project-summary.pdf)** — full write-up of goals, requirements, and implementation

---

## What I'd Improve Next

A few things I'd tackle if I kept building this out:

- Swap flat-file storage for SQLite or an embedded database
- Hash passwords — right now they're stored in plaintext, which is a known limitation
- Make send/request actually peer-to-peer — currently they're one-sided operations
- Add proper input validation for routing numbers, card numbers, and expiry dates
- Rebuild the GUI in JavaFX for a real interface instead of dialog boxes

---

## Author

**Victor Hugo Barbosa**
CS Student — MassBay Community College
[GitHub](https://github.com/victorhubarb) · [LinkedIn](https://www.linkedin.com/in/victorhbarbosa/)
