package Dao;

// Import des packages
import Modele.*;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;

/**
 * Implémentation MySQL du stockage dans la base de données des méthodes définies dans l'interface
 * UtilisateurDAO.
 */
public class UtilisateurDAOImpl implements UtilisateurDAO {
    private DatabaseConnection Data;

    public UtilisateurDAOImpl(DatabaseConnection Data) {
        this.Data = Data;
    }

    @Override
    public ArrayList<Utilisateur> getAll() {
        ArrayList<Utilisateur> listeUtilisateurs = new ArrayList<>();
        try {
            Connection connexion = Data.getConnection();
            Statement statement = connexion.createStatement();
            ResultSet resultats = statement.executeQuery(

        "SELECT u.ID, u.Nom, u.Prenom, u.Email, u.Mdp, p.ID AS patientID, p.type, s.Specialisation, s.Lieu " +
                "FROM utilisateur u " +
                "LEFT JOIN patient p ON u.ID = p.ID " +
                "LEFT JOIN specialiste s ON u.ID = s.ID"
            );

            while (resultats.next()) {
                int id = resultats.getInt("ID");
                String nom = resultats.getString("Nom");
                String prenom = resultats.getString("Prenom");
                String email = resultats.getString("Email");
                String mdp = resultats.getString("Mdp");
                int type = resultats.getInt("type");

                if (resultats.getInt("patientID") != 0) {
                    listeUtilisateurs.add(new Patient(id, nom, prenom, email, mdp, type));
                } else if (resultats.getString("Specialisation") != null) {
                    String specialisation = resultats.getString("Specialisation");
                    String lieu = resultats.getString("Lieu");
                    listeUtilisateurs.add(new Specialiste(id, nom, prenom, email, mdp, specialisation, lieu));
                } else {
                    listeUtilisateurs.add(new Utilisateur(id, nom, prenom, email, mdp));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Création de la liste des utilisateurs impossible");
        }
        return listeUtilisateurs;
    }

    @Override
    public void ajouter(Utilisateur utilisateur) {
        try {
            Connection connexion = Data.getConnection();
            String query = "INSERT INTO utilisateur (Nom, Prenom, Email, Mdp) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getPrenom());
            preparedStatement.setString(3, utilisateur.getEmail());
            preparedStatement.setString(4, utilisateur.getMdp());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                utilisateur.setId(id);
                if (utilisateur instanceof Patient) {
                    Patient patient = (Patient) utilisateur;
                    String patientQuery = "INSERT INTO patient (ID, type) VALUES (?, ?)";
                    PreparedStatement ps = connexion.prepareStatement(patientQuery);
                    ps.setInt(1, id);
                    ps.setInt(2, patient.getType());
                    ps.executeUpdate();
                } else if (utilisateur instanceof Specialiste) {
                    Specialiste specialiste = (Specialiste) utilisateur;
                    String specialisteQuery = "INSERT INTO specialiste (ID, Specialisation, Lieu) VALUES (?, ?, ?)";
                    PreparedStatement ps = connexion.prepareStatement(specialisteQuery);
                    ps.setInt(1, id);
                    ps.setString(2, specialiste.getSpecialisation());
                    ps.setString(3, specialiste.getLieu());
                    ps.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout de l'utilisateur impossible");
        }
    }

    @Override
    public Utilisateur chercher(int id) {
        Utilisateur utilisateur = null;
        try {
            Connection connexion = Data.getConnection();
            String query =
            "SELECT u.ID, u.Nom, u.Prenom, u.Email, u.Mdp, p.ID AS patientID, p.type, s.Specialisation, s.Lieu " +
            "FROM utilisateur u " +
            "LEFT JOIN patient p ON u.ID = p.ID " +
            "LEFT JOIN specialiste s ON u.ID = s.ID " +
            "WHERE u.ID = ?";
            PreparedStatement preparedStatement = connexion.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultats = preparedStatement.executeQuery();

            if (resultats.next()) {
                String nom = resultats.getString("Nom");
                String prenom = resultats.getString("Prenom");
                String email = resultats.getString("Email");
                String mdp = resultats.getString("Mdp");
                int type = resultats.getInt("type");

                if (resultats.getInt("patientID") != 0) {
                    utilisateur = new Patient(id, nom, prenom, email, mdp, type);
                } else if (resultats.getString("Specialisation") != null) {
                    String specialisation = resultats.getString("Specialisation");
                    String lieu = resultats.getString("Lieu");
                    utilisateur = new Specialiste(id, nom, prenom, email, mdp, specialisation, lieu);
                } else {
                    utilisateur = new Utilisateur(id, nom, prenom, email, mdp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Utilisateur non trouvé dans la base de données");
        }
        return utilisateur;
    }
}
