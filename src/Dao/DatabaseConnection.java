package Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static String url;
    private String username;
    private String password;

    private DatabaseConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Méthode permettant d'obtenir une instance de DatabaseConnection
     * @param database Nom de la base de données
     * @param username Nom d'utilisateur
     * @param password Mot de passe
     * @return Instance de DaoFactory
     */
    public static DatabaseConnection getInstance(String database, String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur : Pilote JDBC non trouvé");
            e.printStackTrace();
        }

        url = "jdbc:mysql://localhost:3306/" + database + "?serverTimezone=UTC";
        return new DatabaseConnection(url, username, password);
    }

    /**
     * Méthode pour obtenir une connexion à la base de données
     * @return Connection JDBC
     * @throws SQLException en cas d'échec de connexion
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }


}
