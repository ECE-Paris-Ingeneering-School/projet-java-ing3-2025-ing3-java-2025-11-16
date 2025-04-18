package Dao;

import Modele.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RendezVousDAOImpl implements RendezVousDAO {
    private final DatabaseConnection db;

    public RendezVousDAOImpl(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public List<RendezVous> getAllRendezVous() {
        List<RendezVous> rendezVousList = new ArrayList<>();
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

    @Override
    public List<RendezVous> getRendezVousBySpecialiste(int idSpecialiste) {
        List<RendezVous> liste = new ArrayList<>();
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

    @Override
    public List<RendezVous> getRendezVousByPatient(int idPatient) {
        List<RendezVous> liste = new ArrayList<>();
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
     * Méthode utilitaire pour mapper un ResultSet vers un objet RendezVous
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
