package org.chem.Dao;

import org.chem.Modele.RendezVous;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface RendezVousDAO pour la gestion des rendez-vous dans la base de données.
 * Cette classe permet de manipuler les données relatives aux rendez-vous des patients avec les spécialistes.
 */
public class RendezVousDAOImpl implements RendezVousDAO {

    /** Instance de la connexion à la base de données */
    private final DatabaseConnection db;

    /**
     * Constructeur de la classe RendezVousDAOImpl.
     *
     * @param db Connexion à la base de données
     */
    public RendezVousDAOImpl(DatabaseConnection db) {
        this.db = db;
    }

    /**
     * Récupère tous les rendez-vous présents dans la base de données.
     *
     * @return Une liste d'objets RendezVous correspondant à tous les rendez-vous
     */
    @Override
    public ArrayList<RendezVous> getAllRendezVous() {
        ArrayList<RendezVous> rendezVousList = new ArrayList<>();
        String query = "SELECT * FROM rdv";

        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                RendezVous rdv = new RendezVous();
                rdv.setId(rs.getInt("ID"));
                rdv.setIdSpecialiste(rs.getInt("IDSpecialiste"));
                rdv.setIdPatient(rs.getInt("IDPatient"));
                rdv.setIdHoraire(rs.getInt("IDHoraire"));
                rdv.setDate(rs.getDate("Date"));
                rdv.setNotes(rs.getString("Notes"));
                rdv.setLieu(rs.getString("Lieu"));

                rendezVousList.add(rdv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("❌ Erreur lors de la récupération des rendez-vous.");
        }

        return rendezVousList;
    }

    /**
     * Récupère un rendez-vous à partir de son ID.
     *
     * @param id L'ID du rendez-vous
     * @return L'objet RendezVous correspondant à l'ID ou null si l'ID n'existe pas
     */
    @Override
    public RendezVous getRendezVousById(int id) {
        String query = "SELECT * FROM rdv WHERE ID = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return mapResultSetToRendezVous(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Récupère tous les rendez-vous d'un spécialiste en particulier.
     *
     * @param idSpecialiste L'ID du spécialiste
     * @return Une liste de RendezVous du spécialiste triés par date et horaire
     */
    @Override
    public ArrayList<RendezVous> getRendezVousBySpecialiste(int idSpecialiste) {
        ArrayList<RendezVous> liste = new ArrayList<>();
        String query = "SELECT * FROM rdv WHERE IDSpecialiste = ? ORDER BY Date, IDHoraire";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idSpecialiste);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                liste.add(mapResultSetToRendezVous(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    /**
     * Récupère tous les rendez-vous d'un patient en particulier.
     *
     * @param idPatient L'ID du patient
     * @return Une liste de RendezVous du patient triés par date et horaire
     */
    @Override
    public ArrayList<RendezVous> getRendezVousByPatient(int idPatient) {
        ArrayList<RendezVous> liste = new ArrayList<>();
        String query = "SELECT * FROM rdv WHERE IDPatient = ? ORDER BY Date, IDHoraire";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idPatient);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                liste.add(mapResultSetToRendezVous(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    /**
     * Ajoute un rendez-vous dans la base de données.
     *
     * @param rendezVous L'objet RendezVous à ajouter
     * @return true si l'ajout a réussi, sinon false
     */
    @Override
    public boolean ajouterRendezVous(RendezVous rendezVous) {
        boolean result = false;
        String query = "INSERT INTO rdv (IDSpecialiste, IDPatient, IDHoraire, Date, Notes, Lieu) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, rendezVous.getIdSpecialiste());
            stmt.setInt(2, rendezVous.getIdPatient());
            stmt.setInt(3, rendezVous.getIdHoraire());
            stmt.setDate(4, new java.sql.Date(rendezVous.getDate().getTime()));
            stmt.setString(5, rendezVous.getNotes());
            stmt.setString(6, rendezVous.getLieu());

            int rowsInserted = stmt.executeUpdate();

            if (rowsInserted > 0) {
                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    rendezVous.setId(generatedKeys.getInt(1));
                }
                result = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Vérifie si un horaire est déjà réservé pour une date spécifique.
     *
     * @param idHoraire L'ID de l'horaire
     * @param dateUtil La date du rendez-vous
     * @return true si l'horaire est déjà réservé, sinon false
     */
    @Override
    public boolean estDejaReserve(int idHoraire, java.util.Date dateUtil) {
        System.out.println(idHoraire);
        System.out.println(dateUtil);
        // Conversion de java.util.Date en java.sql.Date
        java.sql.Date dateSQL = new java.sql.Date(dateUtil.getTime());

        String query = "SELECT COUNT(*) FROM rdv WHERE IDHoraire = ? AND Date = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, idHoraire);
            stmt.setDate(2, dateSQL);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Supprime un rendez-vous à partir de son ID.
     *
     * @param id L'ID du rendez-vous à supprimer
     * @return true si la suppression a réussi, sinon false
     */
    @Override
    public boolean supprimerRendezVous(int id) {
        String query = "DELETE FROM rdv WHERE ID = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Modifie un rendez-vous existant dans la base de données.
     *
     * @param rendezVous L'objet RendezVous à modifier
     * @return true si la modification a réussi, sinon false
     */
    @Override
    public boolean modifierRendezVous(RendezVous rendezVous) {
        String query = "UPDATE rdv SET IDSpecialiste = ?, IDPatient = ?, IDHoraire = ?, Date = ?, Notes = ?, Lieu = ? WHERE ID = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, rendezVous.getIdSpecialiste());
            stmt.setInt(2, rendezVous.getIdPatient());
            stmt.setInt(3, rendezVous.getIdHoraire());
            stmt.setDate(4, new java.sql.Date(rendezVous.getDate().getTime()));
            stmt.setString(5, rendezVous.getNotes());
            stmt.setString(6, rendezVous.getLieu());
            stmt.setInt(7, rendezVous.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Méthode utilitaire pour mapper un ResultSet vers un objet RendezVous.
     *
     * @param rs Le ResultSet à mapper
     * @return L'objet RendezVous correspondant
     * @throws SQLException Si une erreur SQL survient lors du mappage
     */
    private RendezVous mapResultSetToRendezVous(ResultSet rs) throws SQLException {
        RendezVous rv = new RendezVous();
        rv.setId(rs.getInt("ID"));
        rv.setIdSpecialiste(rs.getInt("IDSpecialiste"));
        rv.setIdPatient(rs.getInt("IDPatient"));
        rv.setIdHoraire(rs.getInt("IDHoraire"));
        rv.setDate(rs.getDate("Date"));
        rv.setNotes(rs.getString("Notes"));
        rv.setLieu(rs.getString("Lieu"));
        return rv;
    }
}
