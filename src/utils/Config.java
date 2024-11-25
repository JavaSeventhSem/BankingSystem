package utils;

public class Config {
    // Database configuration
    public static final String DB_URL = "jdbc:mysql://localhost:3306/bank_db?useSSL=false";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "";
    
    // Application configuration
    public static final double MAX_DEPOSIT = 100000.0;
    public static final double MAX_WITHDRAWAL = 50000.0;
    public static final int MIN_AGE = 18;
    public static final int MAX_AGE = 100;
}