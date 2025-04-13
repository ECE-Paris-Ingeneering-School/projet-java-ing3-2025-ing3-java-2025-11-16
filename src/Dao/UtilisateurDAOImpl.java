package Dao;

// Import des packages
import Modele.*;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.*;

/**
 * Implémentation MySQL du stockage dans la base de données des méthodes définies dans l'interface
 * UtilisateurDAO.
 */
public class UtilisateurDAOImpl implements UtilisateurDAO {
    private DatabaseConnection Data;

    private int convertirJourEnInt(String jour) {
        return switch (jour.toLowerCase()) {
            case "lundi" -> 1;
            case "mardi" -> 2;
            case "mercredi" -> 3;
            case "jeudi" -> 4;
            case "vendredi" -> 5;
            case "samedi" -> 6;
            case "dimanche" -> 7;
            default -> throw new IllegalArgumentException("Jour invalide : " + jour);
        };
    }

    public UtilisateurDAOImpl(DatabaseConnection Data) {
        this.Data = Data;
    }

    @Override
    public ArrayList<Specialiste> rechercherSpecialistes(String motCle, String jour, Time heure) {
        ArrayList<Specialiste> resultats = new ArrayList<>();

        String sql = """
        SELECT DISTINCT u.ID, u.Nom, u.Prenom, u.Email, u.Mdp, s.Specialisation, s.Lieu
        FROM utilisateur u
        JOIN specialiste s ON u.ID = s.ID
        LEFT JOIN edt e ON s.ID = e.IDSpecialiste
        LEFT JOIN horaire h ON e.IDHoraire = h.ID
        WHERE 
            (u.Nom LIKE ? OR u.Prenom LIKE ? OR s.Specialisation LIKE ? OR s.Lieu LIKE ?)
            AND (? IS NULL OR h.jourSemaine = ?)
            AND (? IS NULL OR h.HeureDebut <= ? AND h.HeureFin > ?)
    """;

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String likeMotCle = "%" + motCle + "%";

            stmt.setString(1, likeMotCle);
            stmt.setString(2, likeMotCle);
            stmt.setString(3, likeMotCle);
            stmt.setString(4, likeMotCle);

            if (jour != null) {
                int jourInt = convertirJourEnInt(jour); // au lieu de parseInt
                stmt.setString(5, jour);
                stmt.setInt(6, jourInt);
            } else {
                stmt.setNull(5, Types.VARCHAR);
                stmt.setNull(6, Types.INTEGER);
            }


            if (heure != null) {
                stmt.setTime(7, heure);
                stmt.setTime(8, heure);
                stmt.setTime(9, heure);
            } else {
                stmt.setNull(7, Types.TIME);
                stmt.setNull(8, Types.TIME);
                stmt.setNull(9, Types.TIME);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Specialiste sp = new Specialiste(
                        rs.getInt("ID"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Email"),
                        rs.getString("Mdp"),
                        rs.getString("Specialisation"),
                        rs.getString("Lieu")
                );
                resultats.add(sp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultats;
    }


    @Override
    public Utilisateur seConnecter(String email, String mdp, String type) {
        Utilisateur utilisateur = null;

        String query =
                "SELECT u.ID, u.Nom, u.Prenom, u.Email, u.Mdp, p.type, s.Specialisation, s.Lieu " +
                        "FROM utilisateur u " +
                        "LEFT JOIN patient p ON u.ID = p.ID " +
                        "LEFT JOIN specialiste s ON u.ID = s.ID " +
                        "WHERE u.Email = ? AND u.Mdp = ?";

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, mdp);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("ID");
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");

                // Identifie selon le type demandé
                switch (type.toLowerCase()) {
                    case "patient":
                        int patientType = rs.getInt("type");
                        utilisateur = new Patient(id, nom, prenom, email, mdp, patientType);
                        break;
                    case "specialiste":
                        String specialisation = rs.getString("Specialisation");
                        String lieu = rs.getString("Lieu");
                        utilisateur = new Specialiste(id, nom, prenom, email, mdp, specialisation, lieu);
                        break;
                    case "admin":
                        utilisateur = new Utilisateur(id, nom, prenom, email, mdp);
                        break;
                    default:
                        System.out.println("Type d'utilisateur inconnu");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
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

    @Override
    public Utilisateur getUtilisateurById(int id) {
        Utilisateur utilisateur = null;
        String query = "SELECT * FROM utilisateur WHERE ID = ?";

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(rs.getInt("ID"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setMdp(rs.getString("mdp"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }


    @Override
    public Utilisateur getUtilisateurByEmail(String email) {
        Utilisateur utilisateur = null;
        String query = "SELECT * FROM utilisateur WHERE email = ?";

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setId(rs.getInt("ID"));
                utilisateur.setNom(rs.getString("nom"));
                utilisateur.setPrenom(rs.getString("prenom"));
                utilisateur.setEmail(rs.getString("email"));
                utilisateur.setMdp(rs.getString("mdp"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la récupération de l'utilisateur par email.");
        }

        return utilisateur;
    }


}
