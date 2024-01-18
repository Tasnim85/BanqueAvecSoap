package DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import metier.Account;

public class AccountDAO {
    
    public static void createAccount(String code, float solde) {
    Connection connection = LaConnexion.seConnecter();
    Account account = new Account(code, solde);
    try {
        Date currentDate = new Date();
        // Check if the account with the given code already exists
        if (!accountExists(account.getCode())) {
            String query = "INSERT INTO accounts (code, solde, dateCreation) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, account.getCode());
                preparedStatement.setDouble(2, account.getSolde());
                preparedStatement.setDate(3, new java.sql.Date(currentDate.getTime()));
                preparedStatement.executeUpdate();
                System.out.println("Account created successfully.");
            }
        } else {
            System.out.println("Account with code " + account.getCode() + " already exists.");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    
    public static boolean accountExists(String code) throws SQLException {
        Connection connection = LaConnexion.seConnecter();
        String query = "SELECT COUNT(*) AS count FROM accounts WHERE code = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, code);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        }
        return false;
    }

    public static Account getAccountByCode(String code) {
        Connection connection = LaConnexion.seConnecter();
        Account account = null;
        try {
            String query = "SELECT * FROM accounts WHERE code = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, code);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        account = new Account(
                                resultSet.getString("code"),
                                resultSet.getFloat("solde")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return account;
    }

    public static void updateAccount(String code, Float solde) {
        Connection connection = LaConnexion.seConnecter();
        Account account = new Account(code, solde);
        try {
            String query = "UPDATE accounts SET solde = ? WHERE code = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDouble(1, account.getSolde());
                preparedStatement.setString(2, account.getCode());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAccount(String code) {
        Connection connection = LaConnexion.seConnecter();
        try {
            String query = "DELETE FROM accounts WHERE code = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, code);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
