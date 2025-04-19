package org.chem.Dao;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {
    private static DatabaseConnection instance;
    private static String url;
    private String username;
    private String password;

    public DatabaseConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Méthode permettant d'obtenir une instance de DatabaseConnection
     * @param databaseUrl Url de la base de données
     * @param username Nom d'utilisateur
     * @param password Mot de passe
     * @return Instance de DaoFactory
     */
    public static DatabaseConnection getInstance(String databaseUrl, String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur : Pilote JDBC non trouvé");
            e.printStackTrace();
        }

        return new DatabaseConnection(databaseUrl, username, password);
    }

    public static DatabaseConnection getInstance() {
        if (instance == null) {
            try {
                Properties props = new Properties();
                InputStream input = DatabaseConnection.class.getResourceAsStream("/connection.properties");

                if (input == null) {
                    throw new RuntimeException("Fichier connection.properties non trouvé !");
                }

                props.load(input);
                String databaseUrl = props.getProperty("databaseUrl");
                String username = props.getProperty("username");
                String password = props.getProperty("password");

                instance = new DatabaseConnection(databaseUrl, username, password);
            } catch (Exception e) {
                System.out.println("Erreur lors de l'initialisation de la connexion à la BDD :");
                e.printStackTrace();
            }
        }
        return instance;
    }
    /**
     * Méthode pour obtenir une connexion à la base de données
     * @return Connection JDBC
     * @throws SQLException en cas d'échec de connexion
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     *     Fermer la connexion à la base de données
     */
    public void disconnect() {
        Connection connexion = null;

        try {
            // création d'un ordre SQL (statement)
            connexion = this.getConnection();
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de déconnexion à la base de données");
        }
    }


    public UtilisateurDAO getUtilisateurDAO() { return new UtilisateurDAOImpl(this);}

    public RendezVousDAO getRendezVousDAO() { return new RendezVousDAOImpl(this);}

    public HoraireDAO getHoraireDAO() { return new HoraireDAOImpl(this);}

    public EdtDAO getEdtDAO() { return new EdtDAOImpl(this);}


}
