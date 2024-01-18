package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LaConnexion {
    private static Connection con;

    public static Connection seConnecter() {
        if (con == null) {
            try {
                // Load the MySQL JDBC driver
                Class.forName("com.mysql.jdbc.Driver");

                // Connection URL for MySQL
                String connectionUrl = "jdbc:mysql://localhost:3306/banque";

                con = DriverManager.getConnection(connectionUrl, "root", "");

                System.out.println("Connexion établie avec la base de données");
            } catch (ClassNotFoundException e) {
                System.out.println("Driver MySQL non trouvé : " + e.getMessage());
            } catch (SQLException ex) {
                System.out.println("Problème lors de la connexion à la base de données MySQL : " + ex.getMessage());
            }
        }
        return con;
    }
}
