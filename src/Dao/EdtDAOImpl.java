package Dao;

import Modele.Edt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EdtDAOImpl implements EdtDAO {
    private DatabaseConnection db;

    public EdtDAOImpl(DatabaseConnection db) {
        this.db = db;
    }

    @Override
    public Edt getEdtById(int id) {
        String sql = "SELECT * FROM edt WHERE ID = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Edt edt = new Edt();
                edt.setId(rs.getInt("ID"));
                edt.setIdSpecialiste(rs.getInt("IDSpecialiste"));
                edt.setIdHoraire(rs.getInt("IDHoraire"));
                return edt;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Edt> getEdtBySpecialiste(int idSpecialiste) {
        List<Edt> liste = new ArrayList<>();
        String sql = "SELECT * FROM edt WHERE IDSpecialiste = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idSpecialiste);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Edt edt = new Edt();
                edt.setId(rs.getInt("ID"));
                edt.setIdSpecialiste(rs.getInt("IDSpecialiste"));
                edt.setIdHoraire(rs.getInt("IDHoraire"));
                liste.add(edt);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return liste;
    }

    @Override
    public boolean ajouterEdt(Edt edt) {
        String sql = "INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES (?, ?)";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, edt.getIdSpecialiste());
            stmt.setInt(2, edt.getIdHoraire());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean supprimerEdt(int id) {
        String sql = "DELETE FROM edt WHERE ID = ?";
        try (Connection conn = db.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
