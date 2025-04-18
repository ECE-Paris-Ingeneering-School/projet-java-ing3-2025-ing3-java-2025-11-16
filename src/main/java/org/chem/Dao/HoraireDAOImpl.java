package org.chem.Dao;

import org.chem.Modele.Horaire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class HoraireDAOImpl implements HoraireDAO {
    private DatabaseConnection db;

    public HoraireDAOImpl(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public Horaire getHoraireById(int id) {
        String sql = "SELECT * FROM horaire WHERE ID = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Horaire h = new Horaire();
                h.setId(rs.getInt("ID"));
                h.setJourSemaine(rs.getInt("jourSemaine"));
                h.setHeureDebut(rs.getTime("HeureDebut"));
                h.setHeureFin(rs.getTime("HeureFin"));
                return h;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Horaire> getAllHoraires() {
        List<Horaire> list = new ArrayList<>();
        String sql = "SELECT * FROM horaire";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Horaire h = new Horaire();
                h.setId(rs.getInt("ID"));
                h.setJourSemaine(rs.getInt("jourSemaine"));
                h.setHeureDebut(rs.getTime("HeureDebut"));
                h.setHeureFin(rs.getTime("HeureFin"));
                list.add(h);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean ajouterHoraire(Horaire horaire) {
        String sql = "INSERT INTO horaire (jourSemaine, HeureDebut, HeureFin) VALUES (?, ?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, horaire.getJourSemaine());
            stmt.setTime(2, horaire.getHeureDebut());
            stmt.setTime(3, horaire.getHeureFin());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean supprimerHoraire(int id) {
        String sql = "DELETE FROM horaire WHERE ID = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean mettreAJourHoraire(Horaire horaire) {
        String sql = "UPDATE horaire SET jourSemaine = ?, HeureDebut = ?, HeureFin = ? WHERE ID = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, horaire.getJourSemaine());
            stmt.setTime(2, horaire.getHeureDebut());
            stmt.setTime(3, horaire.getHeureFin());
            stmt.setInt(4, horaire.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
