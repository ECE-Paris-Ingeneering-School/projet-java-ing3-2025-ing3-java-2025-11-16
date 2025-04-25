package org.chem.Dao;

import org.chem.Modele.Edt;

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
    public boolean ajouterLienSpecialisteHoraire(int idSpecialiste, int idHoraire) {
        try (Connection conn = db.getConnection()) {
            // Vérifie si le lien existe déjà
            String checkSql = "SELECT * FROM edt WHERE IDSpecialiste = ? AND IDHoraire = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, idSpecialiste);
            checkStmt.setInt(2, idHoraire);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) return false;

            // Ajoute le lien
            String insertSql = "INSERT INTO edt (IDSpecialiste, IDHoraire) VALUES (?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(insertSql);
            insertStmt.setInt(1, idSpecialiste);
            insertStmt.setInt(2, idHoraire);
            insertStmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean supprimerLienSpecialisteHoraire(int idSpecialiste, int idHoraire) {
        try (Connection conn = db.getConnection()) {
            String deleteSql = "DELETE FROM edt WHERE IDSpecialiste = ? AND IDHoraire = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, idSpecialiste);
            deleteStmt.setInt(2, idHoraire);

            int rowsAffected = deleteStmt.executeUpdate();
            return rowsAffected > 0; // true si quelque chose a été supprimé
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean modifierLienHoraireSpecialiste(int idSpecialiste, int ancienIdHoraire, int nouveauIdHoraire) {
        try (Connection conn = db.getConnection()) {
            // Vérifie d'abord si le lien avec le nouveau horaire existe déjà
            String checkSql = "SELECT * FROM edt WHERE IDSpecialiste = ? AND IDHoraire = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, idSpecialiste);
            checkStmt.setInt(2, nouveauIdHoraire);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Si le lien existe déjà, on retourne false (pas de modification)
                return false;
            }

            // Si le lien n'existe pas, on peut procéder à la mise à jour
            String updateSql = "UPDATE edt SET IDHoraire = ? WHERE IDSpecialiste = ? AND IDHoraire = ?";
            PreparedStatement stmt = conn.prepareStatement(updateSql);

            stmt.setInt(1, nouveauIdHoraire); // Le nouvel horaire
            stmt.setInt(2, idSpecialiste);     // L'ID du spécialiste
            stmt.setInt(3, ancienIdHoraire);   // L'ancien horaire à remplacer

            int rowsAffected = stmt.executeUpdate();

            // Si aucune ligne n'a été affectée, cela signifie que l'association n'a pas été trouvée
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }




}
