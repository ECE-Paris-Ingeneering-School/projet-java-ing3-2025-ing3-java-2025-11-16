package Dao;

import Modele.Utilisateur;
import Modele.Patient;
import Modele.Specialiste;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAOImpl implements UtilisateurDAO {
    private DatabaseConnection Data;

    public UtilisateurDAOImpl(DatabaseConnection Data) {
        this.Data = Data;
    }

    @Override
    public List<Utilisateur> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = new ArrayList<>();
        String query = "SELECT * FROM Utilisateur";

        try (Connection connection = Data.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("IDUtilisateur");
                String nom = rs.getString("nom");
                String prenom = rs.getString("prénom");
                String email = rs.getString("Email");
                String mdp = rs.getString("mdp");

                // Vérifier si l'utilisateur est un Patient ou un Spécialiste
                if (isPatient(id, connection)) {
                    int typePatient = getTypePatient(id, connection);
                    utilisateurs.add(new Patient(id, nom, prenom, email, mdp, typePatient));
                } else if (isSpecialiste(id, connection)) {
                    String specialisation = getSpecialisation(id, connection);
                    String lieu = getLieu(id, connection);
                    utilisateurs.add(new Specialiste(id, nom, prenom, email, mdp, specialisation, lieu));
                } else {
                    utilisateurs.add(new Utilisateur(id, nom, prenom, email, mdp));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateurs;
    }

    @Override
    public Utilisateur getUtilisateurById(int id) {
        String query = "SELECT * FROM Utilisateur WHERE IDUtilisateur = ?";

        try (Connection connection = Data.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("nom");
                String prenom = rs.getString("prénom");
                String email = rs.getString("Email");
                String mdp = rs.getString("mdp");

                if (isPatient(id, connection)) {
                    int typePatient = getTypePatient(id, connection);
                    return new Patient(id, nom, prenom, email, mdp, typePatient);
                } else if (isSpecialiste(id, connection)) {
                    String specialisation = getSpecialisation(id, connection);
                    String lieu = getLieu(id, connection);
                    return new Specialiste(id, nom, prenom, email, mdp, specialisation, lieu);
                } else {
                    return new Utilisateur(id, nom, prenom, email, mdp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void ajouterUtilisateur(Utilisateur utilisateur) {
        String query = "INSERT INTO Utilisateur (IDUtilisateur, nom, prénom, Email, mdp) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = Data.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, utilisateur.getId());
            statement.setString(2, utilisateur.getNom());
            statement.setString(3, utilisateur.getPrenom());
            statement.setString(4, utilisateur.getEmail());
            statement.setString(5, utilisateur.getMdp());
            statement.executeUpdate();

            if (utilisateur instanceof Patient) {
                ajouterPatient((Patient) utilisateur, connection);
            } else if (utilisateur instanceof Specialiste) {
                ajouterSpecialiste((Specialiste) utilisateur, connection);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void supprimerUtilisateur(int id) {
        String query = "DELETE FROM Utilisateur WHERE IDUtilisateur = ?";

        try (Connection connection = Data.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.executeUpdate();

            supprimerPatient(id, connection);
            supprimerSpecialiste(id, connection);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Vérification si l'utilisateur est un patient
    private boolean isPatient(int id, Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) FROM Patient WHERE IDpatient = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private int getTypePatient(int id, Connection connection) throws SQLException {
        String query = "SELECT TypePatient FROM Patient WHERE IDpatient = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) return rs.getInt("TypePatient");
        }
        return 0;
    }

    private void ajouterPatient(Patient patient, Connection connection) throws SQLException {
        String query = "INSERT INTO Patient (IDpatient, TypePatient) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, patient.getId());
            statement.setInt(2, patient.getTypePatient());
            statement.executeUpdate();
        }
    }

    private void supprimerPatient(int id, Connection connection) throws SQLException {
        String query = "DELETE FROM Patient WHERE IDpatient = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    // Vérification si l'utilisateur est un specialiste
    private boolean isSpecialiste(int id, Connection connection) throws SQLException {
        String query = "SELECT COUNT(*) FROM Specialiste WHERE IDSpecialiste = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private String getSpecialisation(int id, Connection connection) throws SQLException {
        String query = "SELECT Specialisation FROM Specialiste WHERE IDSpecialiste = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) return rs.getString("Specialisation");
        }
        return null;
    }

    private String getLieu(int id, Connection connection) throws SQLException {
        String query = "SELECT Lieu FROM Specialiste WHERE IDSpecialiste = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) return rs.getString("Lieu");
        }
        return null;
    }

    private void ajouterSpecialiste(Specialiste specialiste, Connection connection) throws SQLException {
        String query = "INSERT INTO Specialiste (IDSpecialiste, Specialisation, Lieu) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, specialiste.getId());
            statement.setString(2, specialiste.getSpecialisation());
            statement.setString(3, specialiste.getLieu());
            statement.executeUpdate();
        }
    }

    private void supprimerSpecialiste(int id, Connection connection) throws SQLException {
        String query = "DELETE FROM Specialiste WHERE IDSpecialiste = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }
}
