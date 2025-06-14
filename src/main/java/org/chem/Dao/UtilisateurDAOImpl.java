package org.chem.Dao;

import org.chem.Modele.*;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;
import java.util.*;

/**
 * Classe qui implémente l'interface `UtilisateurDAO` pour interagir avec la base de données.
 * Fournit des méthodes pour la gestion des utilisateurs (connexion, ajout, modification, suppression, recherche).
 */
public class UtilisateurDAOImpl implements UtilisateurDAO {
    private DatabaseConnection Data;

    /**
     * Constructeur de la classe `UtilisateurDAOImpl`.
     * @param Data L'objet de connexion à la base de données.
     */
    public UtilisateurDAOImpl(DatabaseConnection Data) {
        this.Data = Data;
    }

    /**
     * Recherche des spécialistes en fonction de critères tels que le mot-clé, le jour, l'heure et le lieu.
     * @param motCle Le mot-clé à rechercher dans les noms, prénoms et spécialisation.
     * @param jour Le jour de la semaine.
     * @param heure L'heure du rendez-vous.
     * @param lieu Le lieu du spécialiste.
     * @return Une liste de spécialistes qui correspondent aux critères.
     */
    @Override
    public ArrayList<Specialiste> rechercherSpecialistes(String motCle, String jour, Time heure, String lieu) {
        ArrayList<Specialiste> resultats = new ArrayList<>();

        String sql = """
        SELECT DISTINCT u.ID, u.Nom, u.Prenom, u.Email, u.Mdp, s.Specialisation, s.Lieu
        FROM utilisateur u
        JOIN specialiste s ON u.ID = s.ID
        LEFT JOIN edt e ON s.ID = e.IDSpecialiste
        LEFT JOIN horaire h ON e.IDHoraire = h.ID
        WHERE (
            u.Nom LIKE ? 
            OR u.Prenom LIKE ? 
            OR s.Specialisation LIKE ?
            OR CONCAT(u.Nom, ' ', u.Prenom) LIKE ?
            OR CONCAT(u.Prenom, ' ', u.Nom) LIKE ?
        )
    """;

        if (!lieu.equals("Lieu")) {
            sql += " AND s.Lieu LIKE ?";
        }
        if (jour != null && !jour.trim().isEmpty()) {
            sql += " AND h.jourSemaine = ?";
        }
        if (heure != null) {
            sql += " AND h.HeureDebut = ?";
        }

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            String likeMotCle = "%" + motCle + "%";

            stmt.setString(1, likeMotCle); // Nom
            stmt.setString(2, likeMotCle); // Prénom
            stmt.setString(3, likeMotCle); // Spécialisation
            stmt.setString(4, likeMotCle); // Nom + Prénom
            stmt.setString(5, likeMotCle); // Prénom + Nom

            int index = 6;
            if (!lieu.equals("Lieu")) {
                stmt.setString(index++, "%" + lieu + "%");
            }

            if (jour != null && !jour.trim().isEmpty()) {
                int jourInt = Horaire.convertirJourEnInt(jour);
                stmt.setInt(index++, jourInt);
            }

            if (heure != null) {
                stmt.setTime(index, heure);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Specialiste s = new Specialiste(
                        rs.getInt("ID"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Email"),
                        rs.getString("Mdp"),
                        rs.getString("Specialisation"),
                        rs.getString("Lieu")
                );
                s.setEmploiDuTemps(chargerHorairesPourSpecialiste(s.getId()));
                resultats.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultats;
    }

    /**
     * Charge les horaires d'un spécialiste spécifique.
     * @param idSpecialiste L'ID du spécialiste.
     * @return Une liste d'horaires associés au spécialiste.
     */
    @Override
    public ArrayList<Horaire> chargerHorairesPourSpecialiste(int idSpecialiste) {
        ArrayList<Horaire> horaires = new ArrayList<>();
        String sql = """
        SELECT h.ID, h.jourSemaine, h.HeureDebut, h.HeureFin
        FROM edt e
        JOIN horaire h ON e.IDHoraire = h.ID
        WHERE e.IDSpecialiste = ?
        ORDER BY h.jourSemaine, h.HeureDebut
    """;

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSpecialiste);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Horaire h = new Horaire(
                        rs.getInt("ID"),
                        rs.getInt("jourSemaine"),
                        rs.getTime("HeureDebut"),
                        rs.getTime("HeureFin")
                );
                horaires.add(h);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return horaires;
    }


    /**
     * Permet à un utilisateur de se connecter en vérifiant l'email et le mot de passe.
     * @param email L'email de l'utilisateur.
     * @param mdp Le mot de passe de l'utilisateur.
     * @param type Le type d'utilisateur (patient, spécialiste, admin).
     * @return Un objet Utilisateur correspondant aux identifiants si la connexion est réussie, sinon null.
     */
    @Override
    public Utilisateur seConnecter(String email, String mdp, String type) {
        Utilisateur utilisateur = null;

        String query = """
        SELECT u.ID, u.Nom, u.Prenom, u.Email, u.Mdp, p.ID AS patientID, p.type AS patientType, s.ID AS specialisteID, s.Specialisation AS Specialisation, s.Lieu AS Lieu, a.ID AS adminID
        FROM utilisateur u
        LEFT JOIN patient p ON u.ID = p.ID
        LEFT JOIN specialiste s ON u.ID = s.ID
        LEFT JOIN admin a ON u.ID = a.ID
        WHERE u.Email = ?
    """;

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {

                String hashedMdp = rs.getString("Mdp");

                if (BCrypt.checkpw(mdp, hashedMdp)) {
                    int id = rs.getInt("ID");
                    String nom = rs.getString("Nom");
                    String prenom = rs.getString("Prenom");

                    switch (type.toLowerCase()) {
                        case "patient" -> {
                            if (rs.getInt("patientID") != 0) { // Vérifie si l'utilisateur est un patient
                                int typePatient = rs.getInt("patientType");
                                System.out.println("Patient not connected, type = " + typePatient);

                                utilisateur = new Patient(id, nom, prenom, email, hashedMdp, typePatient);
                            }
                        }
                        case "specialiste" -> {
                            if (rs.getInt("specialisteID") != 0) { // Vérifie si l'utilisateur est un spécialiste
                                String specialisation = rs.getString("Specialisation");
                                String lieu = rs.getString("Lieu");
                                utilisateur = new Specialiste(id, nom, prenom, email, hashedMdp, specialisation, lieu);
                            }
                        }
                        case "admin" -> {
                            if (rs.getInt("adminID") != 0) { // Vérifie si l'utilisateur est un admin
                                utilisateur = new Admin(id, nom, prenom, email, hashedMdp);
                            }
                        }
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }


    /**
     * Ajoute un nouvel utilisateur à la base de données.
     * @param utilisateur L'utilisateur à ajouter (Patient, Spécialiste ou Admin).
     */
    @Override
    public void ajouter(Utilisateur utilisateur) {
        try {
            Connection connexion = Data.getConnection();
            String query = "INSERT INTO utilisateur (Nom, Prenom, Email, Mdp) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connexion.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getPrenom());
            preparedStatement.setString(3, utilisateur.getEmail());

            String hashedPassword = BCrypt.hashpw(utilisateur.getMdp(), BCrypt.gensalt());
            System.out.println("mdp = " + utilisateur.getMdp()+"cripte = " + hashedPassword);

            preparedStatement.setString(4, hashedPassword);

            //preparedStatement.setString(4, utilisateur.getMdp());
            preparedStatement.executeUpdate();

            ResultSet rs = preparedStatement.getGeneratedKeys();

            if (rs.next()) {
                int id = rs.getInt(1);
                utilisateur.setId(id);

                switch (utilisateur) {
                    case Patient patient -> {
                        PreparedStatement ps = connexion.prepareStatement("INSERT INTO patient (ID, type) VALUES (?, ?)");
                        ps.setInt(1, id);
                        ps.setInt(2, patient.getType());
                        ps.executeUpdate();
                    }
                    case Specialiste specialiste -> {
                        PreparedStatement ps = connexion.prepareStatement("INSERT INTO specialiste (ID, Specialisation, Lieu) VALUES (?, ?, ?)");
                        ps.setInt(1, id);
                        ps.setString(2, specialiste.getSpecialisation());
                        ps.setString(3, specialiste.getLieu());
                        ps.executeUpdate();
                    }
                    case Admin admin -> {
                        PreparedStatement ps = connexion.prepareStatement("INSERT INTO admin (ID) VALUES (?)");
                        ps.setInt(1, id);
                        ps.executeUpdate();
                    }
                    default -> {
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère la liste de tous les spécialistes enregistrés dans la base de données.
     * @return Une liste de spécialistes.
     */
    @Override
    public ArrayList<Specialiste> getAllSpecialistes() {
        ArrayList<Specialiste> specialistes = new ArrayList<>();

        String sql = """
        SELECT u.ID, u.Nom, u.Prenom, u.Email, u.Mdp, s.Specialisation, s.Lieu
        FROM utilisateur u
        JOIN specialiste s ON u.ID = s.ID
    """;

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Specialiste s = new Specialiste(
                        rs.getInt("ID"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Email"),
                        rs.getString("Mdp"),
                        rs.getString("Specialisation"),
                        rs.getString("Lieu")
                );
                s.setEmploiDuTemps(chargerHorairesPourSpecialiste(s.getId()));
                specialistes.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return specialistes;
    }

    /**
     * Récupère la liste de tous les patients enregistrés dans la base de données.
     * @return Une liste de patients.
     */
    @Override
    public ArrayList<Patient> getAllPatients() {
        ArrayList<Patient> patients = new ArrayList<>();

        String sql = """
        SELECT u.ID, u.Nom, u.Prenom, u.Email, u.Mdp, p.Type AS PatientType
        FROM utilisateur u
        JOIN patient p ON u.ID = p.ID
    """;

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Patient p = new Patient(
                        rs.getInt("ID"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Email"),
                        rs.getString("Mdp"),
                        rs.getInt("PatientType")
                );
                patients.add(p);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return patients;
    }

    /**
     * Supprime un utilisateur de la base de données.
     * @param utilisateur L'utilisateur à supprimer.
     */
    @Override
    public void supprimer(Utilisateur utilisateur) {
        try {
            Connection connexion = Data.getConnection();

            if (utilisateur instanceof Patient) {
                PreparedStatement ps = connexion.prepareStatement("DELETE FROM patient WHERE ID = ?");
                ps.setInt(1, utilisateur.getId());
                ps.executeUpdate();
            } else if (utilisateur instanceof Specialiste) {
                PreparedStatement ps = connexion.prepareStatement("DELETE FROM specialiste WHERE ID = ?");
                ps.setInt(1, utilisateur.getId());
                ps.executeUpdate();
            } else if (utilisateur instanceof Admin) {
                PreparedStatement ps = connexion.prepareStatement("DELETE FROM admin WHERE ID = ?");
                ps.setInt(1, utilisateur.getId());
                ps.executeUpdate();
            }

            PreparedStatement ps = connexion.prepareStatement("DELETE FROM utilisateur WHERE ID = ?");
            ps.setInt(1, utilisateur.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Modifie les informations d'un utilisateur existant dans la base de données.
     * @param utilisateur L'utilisateur avec les informations modifiées.
     */
    @Override
    public void modifier(Utilisateur utilisateur) {
        try {
            Connection connexion = Data.getConnection();

            String mdpCrypte = BCrypt.hashpw(utilisateur.getMdp(), BCrypt.gensalt());

            PreparedStatement psUtilisateur = connexion.prepareStatement(
                    "UPDATE utilisateur SET Nom = ?, Prenom = ?, Email = ?, Mdp = ? WHERE ID = ?");
            psUtilisateur.setString(1, utilisateur.getNom());
            psUtilisateur.setString(2, utilisateur.getPrenom());
            psUtilisateur.setString(3, utilisateur.getEmail());
            psUtilisateur.setString(4, mdpCrypte);
            psUtilisateur.setInt(5, utilisateur.getId());
            psUtilisateur.executeUpdate();

            if (utilisateur instanceof Patient patient) {
                PreparedStatement ps = connexion.prepareStatement("UPDATE patient SET type = ? WHERE ID = ?");
                ps.setInt(1, patient.getType());
                ps.setInt(2, utilisateur.getId());
                ps.executeUpdate();
            } else if (utilisateur instanceof Specialiste specialiste) {
                PreparedStatement ps = connexion.prepareStatement("UPDATE specialiste SET Specialisation = ?, Lieu = ? WHERE ID = ?");
                ps.setString(1, specialiste.getSpecialisation());
                ps.setString(2, specialiste.getLieu());
                ps.setInt(3, utilisateur.getId());
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Récupère un utilisateur en fonction de son ID.
     * @param id L'ID de l'utilisateur à récupérer.
     * @return L'utilisateur correspondant à l'ID, ou null si l'utilisateur n'existe pas.
     */
    @Override
    public Utilisateur getById(int id) {
        Utilisateur utilisateur = null;

        String query = """
        SELECT u.ID, u.Nom, u.Prenom, u.Email, u.Mdp, 
               p.ID AS patientID, p.type AS patientType, 
               s.ID AS specialisteID, s.Specialisation, s.Lieu,
               a.ID AS adminID
        FROM utilisateur u
        LEFT JOIN patient p ON u.ID = p.ID
        LEFT JOIN specialiste s ON u.ID = s.ID
        LEFT JOIN admin a ON u.ID = a.ID
        WHERE u.ID = ?
    """;

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");
                String email = rs.getString("Email");
                String mdp = rs.getString("Mdp");

                if (rs.getInt("patientID") != 0) {
                    int typePatient = rs.getInt("patientType");
                    utilisateur = new Patient(id, nom, prenom, email, mdp, typePatient);
                } else if (rs.getInt("specialisteID") != 0) {
                    String specialisation = rs.getString("Specialisation");
                    String lieu = rs.getString("Lieu");
                    Specialiste specialiste = new Specialiste(id, nom, prenom, email, mdp, specialisation, lieu);
                    specialiste.setEmploiDuTemps(chargerHorairesPourSpecialiste(id));
                    utilisateur = specialiste;
                } else if (rs.getInt("adminID") != 0) {
                    utilisateur = new Admin(id, nom, prenom, email, mdp);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }


}