import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import database.BankDAO;
import models.BankAccount;
import exceptions.*;

public class Main {
    private static BankAccount currentAccount = null;
    private static final Scanner scanner = new Scanner(System.in);
    private static final BankDAO bankDAO = new BankDAO();

    public static void main(String[] args) {
        while (true) {
            try {
                displayMenu();
                int choice = scanner.nextInt();
                scanner.nextLine(); // Clear buffer
                
                handleMenuChoice(choice);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n=== Banking System ===");
        System.out.println("1. Create Account");
        System.out.println("2. Load Account");
        System.out.println("3. Deposit");
        System.out.println("4. Withdraw");
        System.out.println("5. Account Details");
        System.out.println("6. List All Accounts");
        System.out.println("7. Exit");
        System.out.print("\nEnter your choice: ");
    }

    private static void handleMenuChoice(int choice) throws Exception {
        switch (choice) {
            case 1 -> createAccount();
            case 2 -> loadAccount();
            case 3 -> handleDeposit();
            case 4 -> handleWithdrawal();
            case 5 -> displayCurrentAccount();
            case 6 -> listAllAccounts();
            case 7 -> {
                System.out.println("Thank you for using our banking system!");
                scanner.close();
                System.exit(0);
            }
            default -> System.out.println("Invalid choice!");
        }
    }

    private static void createAccount() throws SQLException {
        try {
            System.out.println("\nEnter Account Number:");
            String accNumber = scanner.next();

            System.out.println("Enter First Name:");
            String firstName = scanner.next();

            System.out.println("Enter Last Name:");
            String lastName = scanner.next();

            System.out.println("Enter Age:");
            int age = scanner.nextInt();

            currentAccount = new BankAccount(accNumber, firstName, lastName, age);
            bankDAO.createAccount(currentAccount);
            System.out.println("Account successfully created!");
            
        } catch (InvalidAgeException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void loadAccount() throws SQLException {
        System.out.println("\nEnter Account Number:");
        String accNumber = scanner.next();

        currentAccount = bankDAO.getAccount(accNumber);
        if (currentAccount == null) {
            System.out.println("Account not found!");
        } else {
            System.out.println("Account loaded successfully!");
            currentAccount.displayDetails();
        }
    }

    private static void handleDeposit() throws SQLException {
        if (!isAccountInitialized()) return;

        try {
            System.out.println("\nEnter amount to deposit:");
            double amount = scanner.nextDouble();
            currentAccount.creditBalance(amount);
            bankDAO.updateBalance(currentAccount.getAccNumber(), currentAccount.getBalance());
            System.out.printf("Successfully deposited %.2f\n", amount);
        } catch (InvalidAmountException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void handleWithdrawal() throws SQLException {
        if (!isAccountInitialized()) return;

        try {
            System.out.println("\nEnter amount to withdraw:");
            double amount = scanner.nextDouble();
            currentAccount.debitBalance(amount);
            bankDAO.updateBalance(currentAccount.getAccNumber(), currentAccount.getBalance());
            System.out.printf("Successfully withdrawn %.2f\n", amount);
        } catch (InvalidAmountException | InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void displayCurrentAccount() {
        if (!isAccountInitialized()) return;
        currentAccount.displayDetails();
    }

    private static void listAllAccounts() throws SQLException {
        List<BankAccount> accounts = bankDAO.getAllAccounts();
        if (accounts.isEmpty()) {
            System.out.println("\nNo accounts found in the system.");
            return;
        }

        System.out.println("\nAll Bank Accounts:");
        System.out.println("----------------------------------------");
        for (BankAccount acc : accounts) {
            acc.displayDetails();
            System.out.println("----------------------------------------");
        }
    }

    private static boolean isAccountInitialized() {
        if (currentAccount == null) {
            System.out.println("Please create or load an account first (Option 1 or 2)");
            return false;
        }
        return true;
    }
}