package org.chem.Dao;

import org.chem.Modele.Horaire;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implémentation de l'interface HoraireDAO pour la gestion des horaires dans la base de données.
 * Cette classe permet de manipuler les données relatives aux horaires des spécialistes.
 */
public class HoraireDAOImpl implements HoraireDAO {

    /** Instance de la connexion à la base de données */
    private DatabaseConnection db;

    /**
     * Constructeur de la classe HoraireDAOImpl.
     *
     * @param db Connexion à la base de données
     */
    public HoraireDAOImpl(DatabaseConnection db) {
        this.db = db;
    }

    /**
     * Récupère un horaire à partir de son ID.
     *
     * @param id L'ID de l'horaire
     * @return L'objet Horaire correspondant à l'ID ou null si l'ID n'existe pas
     */
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

    /**
     * Récupère tous les horaires présents dans la base de données.
     *
     * @return Une liste d'objets Horaire correspondant à tous les horaires
     */
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

    /**
     * Ajoute un nouvel horaire dans la base de données.
     *
     * @param horaire L'objet Horaire à ajouter
     * @return true si l'ajout a réussi, sinon false
     */
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

    /**
     * Supprime un horaire à partir de son ID.
     *
     * @param id L'ID de l'horaire à supprimer
     * @return true si la suppression a réussi, sinon false
     */
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

    /**
     * Met à jour un horaire dans la base de données.
     *
     * @param horaire L'objet Horaire à mettre à jour
     * @return true si la mise à jour a réussi, sinon false
     */
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

    /**
     * Récupère l'ID d'un horaire existant à partir du jour et de l'heure de début.
     *
     * @param jour Le jour de la semaine
     * @param heureDebut L'heure de début de l'horaire
     * @return L'ID de l'horaire si trouvé, sinon -1
     */
    @Override
    public int getIdHoraireExistant(int jour, Time heureDebut) {
        try (Connection conn =db.getConnection()) {
            String sql = "SELECT ID FROM horaire WHERE jourSemaine = ? AND HeureDebut = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, jour);
            stmt.setTime(2, heureDebut);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("ID");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

}
