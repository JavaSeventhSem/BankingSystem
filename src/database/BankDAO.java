package database;

import models.BankAccount;
import java.util.ArrayList;
import java.util.List;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BankDAO {
    public void createAccount(BankAccount account) throws SQLException {
        String sql = "INSERT INTO accounts (acc_number, first_name, last_name, age, balance) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, account.getAccNumber());
            pstmt.setString(2, account.getFirstName());
            pstmt.setString(3, account.getLastName());
            pstmt.setInt(4, account.getAge());
            pstmt.setDouble(5, account.getBalance());

            pstmt.executeUpdate();
        }
    }

    public void updateBalance(String accNumber, double newBalance) throws SQLException {
        String sql = "UPDATE accounts SET balance = ? WHERE acc_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newBalance);
            pstmt.setString(2, accNumber);

            pstmt.executeUpdate();
        }
    }

    public BankAccount getAccount(String accNumber) throws SQLException {
        String sql = "SELECT * FROM accounts WHERE acc_number = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, accNumber);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new BankAccount(
                            rs.getString("acc_number"),
                            rs.getString("first_name"),
                            rs.getString("last_name"),
                            rs.getInt("age"),
                            rs.getDouble("balance"));
                }
                return null;
            }
        }
    }

    public List<BankAccount> getAllAccounts() throws SQLException {
        List<BankAccount> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                accounts.add(new BankAccount(
                        rs.getString("acc_number"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("age"),
                        rs.getDouble("balance")));
            }
        }
        return accounts;
    }
}