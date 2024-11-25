package models;

import exceptions.InvalidAgeException;
import exceptions.InvalidAmountException;
import exceptions.InsufficientBalanceException;
import utils.Config;

public class BankAccount {
    private String accNumber;
    private String firstName;
    private String lastName;
    private int age;
    private double balance;

    // Constructor for new accounts
    public BankAccount(String accNumber, String firstName, String lastName, int age) 
            throws InvalidAgeException {
        
        validateInputs(accNumber, firstName, lastName, age);
        
        this.accNumber = accNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.balance = 0.0;
    }

    // Constructor for loading from database
    public BankAccount(String accNumber, String firstName, String lastName, int age, double balance) {
        this.accNumber = accNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.balance = balance;
    }

    private void validateInputs(String accNumber, String firstName, String lastName, int age) 
            throws InvalidAgeException {
        
        if (age < Config.MIN_AGE || age > Config.MAX_AGE) {
            throw new InvalidAgeException(
                String.format("Age must be between %d and %d years", Config.MIN_AGE, Config.MAX_AGE));
        }
        if (accNumber == null || accNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Account number cannot be empty");
        }
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be empty");
        }
    }

    // Getters
    public String getAccNumber() { return accNumber; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public int getAge() { return age; }
    public double getBalance() { return balance; }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void creditBalance(double amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Credit amount must be positive");
        }
        if (amount > Config.MAX_DEPOSIT) {
            throw new InvalidAmountException(
                String.format("Credit amount cannot exceed %.2f per transaction", Config.MAX_DEPOSIT));
        }

        this.balance += amount;
    }

    public void debitBalance(double amount) throws InvalidAmountException, InsufficientBalanceException {
        if (amount <= 0) {
            throw new InvalidAmountException("Debit amount must be positive");
        }
        if (amount > Config.MAX_WITHDRAWAL) {
            throw new InvalidAmountException(
                String.format("Debit amount cannot exceed %.2f per transaction", Config.MAX_WITHDRAWAL));
        }
        if (balance < amount) {
            throw new InsufficientBalanceException(
                String.format("Insufficient balance. Current balance: %.2f, Requested amount: %.2f", 
                    balance, amount));
        }

        this.balance -= amount;
    }

    public void displayDetails() {
        System.out.println("\nAccount Details:");
        System.out.println("----------------------------------------");
        System.out.println("Account Number: " + accNumber);
        System.out.println("Name: " + getFullName());
        System.out.println("Age: " + age);
        System.out.printf("Current Balance: Rs %.2f\n", balance);
        System.out.println("----------------------------------------");
    }

    @Override
    public String toString() {
        return String.format("Account[%s] %s - Balance: Rs %.2f", 
            accNumber, getFullName(), balance);
    }
}