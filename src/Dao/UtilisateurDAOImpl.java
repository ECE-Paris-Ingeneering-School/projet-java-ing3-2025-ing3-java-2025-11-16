package Dao;

import Modele.*;

import java.sql.*;
import java.util.*;

public class UtilisateurDAOImpl implements UtilisateurDAO {
    private DatabaseConnection Data;

    public UtilisateurDAOImpl(DatabaseConnection Data) {
        this.Data = Data;
    }

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

    @Override
    public ArrayList<Specialiste> rechercherSpecialistes(String motCle, String jour, Time heure, String lieu) {
        ArrayList<Specialiste> resultats = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
        SELECT DISTINCT u.ID, u.Nom, u.Prenom, u.Email, u.Mdp, s.Specialisation, s.Lieu
        FROM utilisateur u
        JOIN specialiste s ON u.ID = s.ID
        LEFT JOIN edt e ON s.ID = e.IDSpecialiste
        LEFT JOIN horaire h ON e.IDHoraire = h.ID
        WHERE 1=1
    """);

        // Liste des paramètres
        List<Object> params = new ArrayList<>();

        // Filtre mot-clé
        if (motCle != null && !motCle.isEmpty()) {
            sql.append(" AND (u.Nom LIKE ? OR u.Prenom LIKE ? OR s.Specialisation LIKE ?)");
            String likeMotCle = "%" + motCle + "%";
            params.add(likeMotCle);
            params.add(likeMotCle);
            params.add(likeMotCle);
        }

        // Filtre lieu
        if (lieu != null && !lieu.isEmpty()) {
            sql.append(" AND s.Lieu LIKE ?");
            params.add("%" + lieu + "%");
        }

        // Filtre jour
        if (jour != null && !jour.isEmpty()) {
            int jourInt = convertirJourEnInt(jour); // 1 = lundi, etc.
            sql.append(" AND h.jourSemaine = ?");
            params.add(jourInt);
        }

        // Filtre heure de début
        if (heure != null) {
            sql.append(" AND h.HeureDebut = ?");
            params.add(heure);
        }

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

            // Insertion des paramètres
            for (int i = 0; i < params.size(); i++) {
                Object param = params.get(i);
                if (param instanceof String)
                    stmt.setString(i + 1, (String) param);
                else if (param instanceof Integer)
                    stmt.setInt(i + 1, (Integer) param);
                else if (param instanceof Time)
                    stmt.setTime(i + 1, (Time) param);
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
                resultats.add(s);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultats;
    }



    @Override
    public Utilisateur seConnecter(String email, String mdp, String type) {
        Utilisateur utilisateur = null;

        String query = """
            SELECT u.ID, u.Nom, u.Prenom, u.Email, u.Mdp, p.type, s.Specialisation, s.Lieu
            FROM utilisateur u
            LEFT JOIN patient p ON u.ID = p.ID
            LEFT JOIN specialiste s ON u.ID = s.ID
            LEFT JOIN admin a ON u.ID = a.ID
            WHERE u.Email = ? AND u.Mdp = ?
        """;

        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, email);
            stmt.setString(2, mdp);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("ID");
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");

                switch (type.toLowerCase()) {
                    case "patient" -> {
                        int patientType = rs.getInt("type");
                        utilisateur = new Patient(id, nom, prenom, email, mdp, patientType);
                    }
                    case "specialiste" -> {
                        String specialisation = rs.getString("Specialisation");
                        String lieu = rs.getString("Lieu");
                        utilisateur = new Specialiste(id, nom, prenom, email, mdp, specialisation, lieu);
                    }
                    case "admin" -> utilisateur = new Admin(id, nom, prenom, email, mdp);
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
            ResultSet resultats = statement.executeQuery("""
                SELECT u.ID, u.Nom, u.Prenom, u.Email, u.Mdp, 
                       p.ID AS patientID, p.type, 
                       s.Specialisation, s.Lieu, 
                       a.ID AS adminID
                FROM utilisateur u 
                LEFT JOIN patient p ON u.ID = p.ID 
                LEFT JOIN specialiste s ON u.ID = s.ID 
                LEFT JOIN admin a ON u.ID = a.ID
            """);

            while (resultats.next()) {
                int id = resultats.getInt("ID");
                String nom = resultats.getString("Nom");
                String prenom = resultats.getString("Prenom");
                String email = resultats.getString("Email");
                String mdp = resultats.getString("Mdp");

                if (resultats.getInt("patientID") != 0) {
                    int type = resultats.getInt("type");
                    listeUtilisateurs.add(new Patient(id, nom, prenom, email, mdp, type));
                } else if (resultats.getString("Specialisation") != null) {
                    String specialisation = resultats.getString("Specialisation");
                    String lieu = resultats.getString("Lieu");
                    listeUtilisateurs.add(new Specialiste(id, nom, prenom, email, mdp, specialisation, lieu));
                } else if (resultats.getInt("adminID") != 0) {
                    listeUtilisateurs.add(new Admin(id, nom, prenom, email, mdp));
                } else {
                    listeUtilisateurs.add(new Utilisateur(id, nom, prenom, email, mdp));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
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

    @Override
    public void modifier(Utilisateur utilisateur) {
        try {
            Connection connexion = Data.getConnection();

            PreparedStatement psUtilisateur = connexion.prepareStatement(
                    "UPDATE utilisateur SET Nom = ?, Prenom = ?, Email = ?, Mdp = ? WHERE ID = ?");
            psUtilisateur.setString(1, utilisateur.getNom());
            psUtilisateur.setString(2, utilisateur.getPrenom());
            psUtilisateur.setString(3, utilisateur.getEmail());
            psUtilisateur.setString(4, utilisateur.getMdp());
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

    @Override
    public Utilisateur chercher(int id) {
        Utilisateur utilisateur = null;
        try {
            Connection connexion = Data.getConnection();
            PreparedStatement stmt = connexion.prepareStatement("""
                SELECT u.ID, u.Nom, u.Prenom, u.Email, u.Mdp, 
                       p.ID AS patientID, p.type, 
                       s.Specialisation, s.Lieu, 
                       a.ID AS adminID
                FROM utilisateur u 
                LEFT JOIN patient p ON u.ID = p.ID 
                LEFT JOIN specialiste s ON u.ID = s.ID 
                LEFT JOIN admin a ON u.ID = a.ID 
                WHERE u.ID = ?
            """);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String nom = rs.getString("Nom");
                String prenom = rs.getString("Prenom");
                String email = rs.getString("Email");
                String mdp = rs.getString("Mdp");

                if (rs.getInt("patientID") != 0) {
                    int type = rs.getInt("type");
                    utilisateur = new Patient(id, nom, prenom, email, mdp, type);
                } else if (rs.getString("Specialisation") != null) {
                    String specialisation = rs.getString("Specialisation");
                    String lieu = rs.getString("Lieu");
                    utilisateur = new Specialiste(id, nom, prenom, email, mdp, specialisation, lieu);
                } else if (rs.getInt("adminID") != 0) {
                    utilisateur = new Admin(id, nom, prenom, email, mdp);
                } else {
                    utilisateur = new Utilisateur(id, nom, prenom, email, mdp);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilisateur;
    }

    @Override
    public Utilisateur getUtilisateurById(int id) {
        Utilisateur utilisateur = null;
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM utilisateur WHERE ID = ?")) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                utilisateur = new Utilisateur(
                        rs.getInt("ID"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Email"),
                        rs.getString("Mdp")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }

    @Override
    public Utilisateur getUtilisateurByEmail(String email) {
        Utilisateur utilisateur = null;
        try (Connection conn = Data.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM utilisateur WHERE Email = ?")) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                utilisateur = new Utilisateur(
                        rs.getInt("ID"),
                        rs.getString("Nom"),
                        rs.getString("Prenom"),
                        rs.getString("Email"),
                        rs.getString("Mdp")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return utilisateur;
    }
}
