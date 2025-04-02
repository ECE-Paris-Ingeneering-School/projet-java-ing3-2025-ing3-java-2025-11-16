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
    public void supprimer(Utilisateur utilisateur) {
        try {
            Connection connexion = Data.getConnection();

            // Supprimer l'utilisateur dans la table 'patient' ou 'specialiste' si nécessaire
            if (utilisateur instanceof Patient) {
                String deletePatient = "DELETE FROM patient WHERE ID = ?";
                PreparedStatement psPatient = connexion.prepareStatement(deletePatient);
                psPatient.setInt(1, utilisateur.getId());
                psPatient.executeUpdate();
            } else if (utilisateur instanceof Specialiste) {
                String deleteSpecialiste = "DELETE FROM specialiste WHERE ID = ?";
                PreparedStatement psSpecialiste = connexion.prepareStatement(deleteSpecialiste);
                psSpecialiste.setInt(1, utilisateur.getId());
                psSpecialiste.executeUpdate();
            }

            // Supprimer l'utilisateur dans la table 'utilisateur'
            String deleteUtilisateur = "DELETE FROM utilisateur WHERE ID = ?";
            PreparedStatement psUtilisateur = connexion.prepareStatement(deleteUtilisateur);
            psUtilisateur.setInt(1, utilisateur.getId());
            psUtilisateur.executeUpdate();

            System.out.println("Utilisateur supprimé avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de l'utilisateur");
        }
    }


    @Override
    public void modifier(Utilisateur utilisateur) {
        try {
            Connection connexion = Data.getConnection();

            // Modifier l'utilisateur dans la table 'utilisateur'
            String updateUtilisateur = "UPDATE utilisateur SET Nom = ?, Prenom = ?, Email = ?, Mdp = ? WHERE ID = ?";
            PreparedStatement psUtilisateur = connexion.prepareStatement(updateUtilisateur);
            psUtilisateur.setString(1, utilisateur.getNom());
            psUtilisateur.setString(2, utilisateur.getPrenom());
            psUtilisateur.setString(3, utilisateur.getEmail());
            psUtilisateur.setString(4, utilisateur.getMdp());
            psUtilisateur.setInt(5, utilisateur.getId());
            psUtilisateur.executeUpdate();

            // Modifier dans la table 'patient' si l'utilisateur est un patient
            if (utilisateur instanceof Patient) {
                Patient patient = (Patient) utilisateur;
                String updatePatient = "UPDATE patient SET type = ? WHERE ID = ?";
                PreparedStatement psPatient = connexion.prepareStatement(updatePatient);
                psPatient.setInt(1, patient.getType());
                psPatient.setInt(2, utilisateur.getId());
                psPatient.executeUpdate();
            }

            // Modifier dans la table 'specialiste' si l'utilisateur est un spécialiste
            else if (utilisateur instanceof Specialiste) {
                Specialiste specialiste = (Specialiste) utilisateur;
                String updateSpecialiste = "UPDATE specialiste SET Specialisation = ?, Lieu = ? WHERE ID = ?";
                PreparedStatement psSpecialiste = connexion.prepareStatement(updateSpecialiste);
                psSpecialiste.setString(1, specialiste.getSpecialisation());
                psSpecialiste.setString(2, specialiste.getLieu());
                psSpecialiste.setInt(3, utilisateur.getId());
                psSpecialiste.executeUpdate();
            }

            System.out.println("Utilisateur mis à jour avec succès");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour de l'utilisateur");
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
