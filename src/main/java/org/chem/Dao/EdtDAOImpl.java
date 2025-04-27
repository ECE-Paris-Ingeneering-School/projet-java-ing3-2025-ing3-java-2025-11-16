package org.chem.Dao;

import org.chem.Modele.Edt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface EdtDAO pour la gestion des emplois du temps (EDT) dans la base de données.
 * Cette classe permet de manipuler les données relatives à l'emploi du temps des spécialistes.
 */
public class EdtDAOImpl implements EdtDAO {

    /** Instance de la connexion à la base de données */
    private DatabaseConnection db;

    /**
     * Constructeur de la classe EdtDAOImpl.
     *
     * @param db Connexion à la base de données
     */
    public EdtDAOImpl(DatabaseConnection db) {
        this.db = db;
    }

    /**
     * Récupère un emploi du temps en fonction de son ID.
     *
     * @param id L'ID de l'emploi du temps
     * @return L'objet Edt correspondant à l'ID ou null si l'ID n'existe pas
     */
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

    /**
     * Récupère la liste des emplois du temps d'un spécialiste à partir de son ID.
     *
     * @param idSpecialiste L'ID du spécialiste
     * @return Une liste d'objets Edt correspondant à l'ID du spécialiste
     */
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

    /**
     * Ajoute un nouvel emploi du temps dans la base de données.
     *
     * @param edt L'objet Edt à ajouter
     * @return true si l'ajout a réussi, sinon false
     */
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

    /**
     * Ajoute un lien entre un spécialiste et un horaire dans la table edt.
     * Cette méthode vérifie si le lien existe déjà avant de l'ajouter.
     *
     * @param idSpecialiste L'ID du spécialiste
     * @param idHoraire L'ID de l'horaire
     * @return true si le lien a été ajouté avec succès, sinon false
     */
    @Override
    public boolean ajouterLienSpecialisteHoraire(int idSpecialiste, int idHoraire) {
        try (Connection conn = db.getConnection()) {
            // Vérifie si le lien existe déjà
            String checkSql = "SELECT * FROM edt WHERE IDSpecialiste = ? AND IDHoraire = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSql);
            checkStmt.setInt(1, idSpecialiste);
            checkStmt.setInt(2, idHoraire);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) return false; // Le lien existe déjà

            // Ajoute le lien si nécessaire
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

    /**
     * Supprime un lien entre un spécialiste et un horaire dans la table edt.
     *
     * @param idSpecialiste L'ID du spécialiste
     * @param idHoraire L'ID de l'horaire
     * @return true si la suppression a réussi, sinon false
     */
    @Override
    public boolean supprimerLienSpecialisteHoraire(int idSpecialiste, int idHoraire) {
        try (Connection conn = db.getConnection()) {
            String deleteSql = "DELETE FROM edt WHERE IDSpecialiste = ? AND IDHoraire = ?";
            PreparedStatement deleteStmt = conn.prepareStatement(deleteSql);
            deleteStmt.setInt(1, idSpecialiste);
            deleteStmt.setInt(2, idHoraire);

            int rowsAffected = deleteStmt.executeUpdate();
            return rowsAffected > 0; // true si la suppression a affecté au moins une ligne
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Modifie un lien entre un spécialiste et un horaire dans la table edt.
     * Le lien avec le nouvel horaire ne doit pas déjà exister.
     *
     * @param idSpecialiste L'ID du spécialiste
     * @param ancienIdHoraire L'ancien ID d'horaire
     * @param nouveauIdHoraire Le nouvel ID d'horaire
     * @return true si la modification a réussi, sinon false
     */
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

            // Met à jour le lien si nécessaire
            String updateSql = "UPDATE edt SET IDHoraire = ? WHERE IDSpecialiste = ? AND IDHoraire = ?";
            PreparedStatement stmt = conn.prepareStatement(updateSql);

            stmt.setInt(1, nouveauIdHoraire); // Le nouvel horaire
            stmt.setInt(2, idSpecialiste);     // L'ID du spécialiste
            stmt.setInt(3, ancienIdHoraire);   // L'ancien horaire à remplacer

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0; // true si la mise à jour a affecté au moins une ligne
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
