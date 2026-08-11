package CODSOFT.Task2_ATM_Interface;

import java.util.ArrayList;
import java.util.List;

public class BankAccount {
    private String accountNumber;
    private String pin;
    private double balance;
    private List<String> transactions;

    public BankAccount(String accountNumber, String pin, double initialBalance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() { return accountNumber; }
    public String getPin() { return pin; }
    public double getBalance() { return balance; }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            transactions.add("Deposited: ₹" + amount);
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive.");
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            transactions.add("Withdrawn: ₹" + amount);
            return true;
        } else if (amount > balance) {
            return false; // Insufficient funds
        } else {
            throw new IllegalArgumentException("Withdrawal amount must be positive.");
        }
    }
    
    public List<String> getTransactionHistory() {
        return transactions;
    }
    
    public void addTransactionRecord(String record) {
        transactions.add(record);
    }
    
    public String getTransactionsAsString() {
        if (transactions.isEmpty()) return "";
        return String.join("|", transactions);
    }
}
