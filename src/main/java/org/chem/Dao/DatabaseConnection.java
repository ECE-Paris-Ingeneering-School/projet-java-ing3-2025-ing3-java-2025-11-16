package org.chem.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe permettant de gérer la connexion à la base de données MySQL.
 * Cette classe fournit des méthodes pour établir une connexion à la base de données et pour obtenir des instances des DAO.
 */
public class DatabaseConnection {

    /** URL de connexion à la base de données */
    private static String url;

    /** Nom d'utilisateur pour la connexion à la base de données */
    private String username;

    /** Mot de passe pour la connexion à la base de données */
    private String password;

    /**
     * Constructeur de la classe DatabaseConnection.
     * Initialise les informations nécessaires pour se connecter à la base de données.
     *
     * @param url URL de la base de données
     * @param username Nom d'utilisateur pour se connecter à la base de données
     * @param password Mot de passe pour se connecter à la base de données
     */
    public DatabaseConnection(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    /**
     * Méthode permettant d'obtenir une instance de DatabaseConnection pour une base de données spécifique.
     *
     * @param database Nom de la base de données
     * @param username Nom d'utilisateur
     * @param password Mot de passe
     * @return Instance de DatabaseConnection
     */
    public static DatabaseConnection getInstance(String database, String username, String password) {
        try {
            // Charge le pilote JDBC pour MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur : Pilote JDBC non trouvé");
            e.printStackTrace();
        }

        // Crée l'URL de connexion avec le nom de la base de données
        url = "jdbc:mysql://localhost:3306/" + database + "?serverTimezone=UTC";
        return new DatabaseConnection(url, username, password);
    }

    /**
     * Méthode permettant d'obtenir une instance par défaut de DatabaseConnection avec des paramètres prédéfinis.
     * Utilise la base de données "rdv_specialiste" et les identifiants par défaut ("root", "root").
     *
     * @return Instance de DatabaseConnection avec des paramètres par défaut
     */
    public static DatabaseConnection getDefaultInstance() {
        return getInstance("rdv_specialiste", "root", "root");
    }

    /**
     * Méthode pour obtenir une connexion à la base de données.
     *
     * @return Connexion JDBC
     * @throws SQLException Si la connexion échoue
     */
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    /**
     * Méthode pour fermer la connexion à la base de données.
     */
    public void disconnect() {
        Connection connexion = null;

        try {
            // Création de la connexion
            connexion = this.getConnection();
            // Fermeture de la connexion
            connexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur de déconnexion à la base de données");
        }
    }

    /**
     * Méthode pour obtenir une instance du DAO Utilisateur.
     *
     * @return Instance de UtilisateurDAO
     */
    public UtilisateurDAO getUtilisateurDAO() {
        return new UtilisateurDAOImpl(this);
    }

    /**
     * Méthode pour obtenir une instance du DAO RendezVous.
     *
     * @return Instance de RendezVousDAO
     */
    public RendezVousDAO getRendezVousDAO() {
        return new RendezVousDAOImpl(this);
    }

    /**
     * Méthode pour obtenir une instance du DAO Horaire.
     *
     * @return Instance de HoraireDAO
     */
    public HoraireDAO getHoraireDAO() {
        return new HoraireDAOImpl(this);
    }

    /**
     * Méthode pour obtenir une instance du DAO Edt.
     *
     * @return Instance de EdtDAO
     */
    public EdtDAO getEdtDAO() {
        return new EdtDAOImpl(this);
    }
}
