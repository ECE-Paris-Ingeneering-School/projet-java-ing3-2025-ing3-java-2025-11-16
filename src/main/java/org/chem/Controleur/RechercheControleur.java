package org.chem.Controleur;

import org.chem.Dao.DatabaseConnection;
import org.chem.Dao.UtilisateurDAOImpl;
import org.chem.Modele.Specialiste;
import org.chem.Vue.Recherche;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;

/**
 * Contrôleur pour la fonctionnalité de recherche de spécialistes.
 * Permet d'effectuer des recherches par nom, spécialité, lieu, jour et heure.
 */
public class RechercheControleur {

    /** La vue associée à ce contrôleur. */
    private Recherche vue;

    /** Le DAO permettant d'interagir avec les spécialistes en base de données. */
    private UtilisateurDAOImpl specialisteDAO;

    /**
     * Constructeur du contrôleur de recherche de spécialistes.
     *
     * @param vue La vue de recherche à contrôler.
     */
    public RechercheControleur(Recherche vue) {
        this.vue = vue;
        DatabaseConnection db = DatabaseConnection.getDefaultInstance();
        this.specialisteDAO = new UtilisateurDAOImpl(db);

        // Affichage des spécialistes au chargement de la vue
        afficherTousLesSpecialistes();

        // Ajout des listeners pour les boutons de recherche
        this.vue.getRechercherBtn().addActionListener(e -> rechercher());
    }

    /**
     * Affiche tous les spécialistes dans la vue.
     * Appelé lors du chargement initial de la vue.
     */
    private void afficherTousLesSpecialistes() {
        ArrayList<Specialiste> tousLesSpecialistes = specialisteDAO.getAllSpecialistes();
        afficherResultats(tousLesSpecialistes);
    }

    /**
     * Effectue une recherche de spécialistes selon les critères fournis dans la vue.
     * Si l'un des champs est invalide ou vide, un message d'erreur est affiché.
     */
    private void rechercher() {
        String motCle = vue.getMotCleField().getText().trim();
        String lieu = vue.getLieuField().getText().trim();
        String jour = null;
        Time heure = null;

        // Récupération du jour sélectionné
        if (vue.getJourCombo().getSelectedItem() != null && !vue.getJourCombo().getSelectedItem().toString().isEmpty()) {
            jour = vue.getJourCombo().getSelectedItem().toString().toLowerCase();
        }

        // Récupération de l'heure sélectionnée
        if (vue.getHeureCombo().getSelectedItem() != null && !vue.getHeureCombo().getSelectedItem().toString().isEmpty()) {
            try {
                heure = Time.valueOf(vue.getHeureCombo().getSelectedItem().toString() + ":00");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(vue, "Heure invalide (ex : 09:00)");
                return;
            }
        }

        // Vérification que le mot-clé de recherche est renseigné
        if (motCle.isEmpty() || motCle.equalsIgnoreCase("Nom, spécialité")) {
            JOptionPane.showMessageDialog(vue, "Saisir un nom ou une spécialité", "Champ requis", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Recherche des spécialistes dans la base de données avec les critères donnés
        ArrayList<Specialiste> resultats = specialisteDAO.rechercherSpecialistes(motCle, jour, heure, lieu);
        afficherResultats(resultats);
    }

    /**
     * Affiche les résultats de la recherche dans la vue.
     * Si aucun spécialiste n'est trouvé, un message est affiché.
     *
     * @param specialistes Liste des spécialistes à afficher dans la vue.
     */
    private void afficherResultats(ArrayList<Specialiste> specialistes) {
        JPanel panel = vue.getResultatsPanel();
        panel.removeAll();

        // Si aucun résultat n'est trouvé, afficher un message
        if (specialistes.isEmpty()) {
            JLabel label = new JLabel("Aucun spécialiste trouvé.");
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            panel.add(label);
        } else {
            // Sinon, afficher chaque spécialiste sous forme de panel
            for (Specialiste s : specialistes) {
                panel.add(vue.creerPanelSpecialiste(s)); // si tu veux que `creerPanelSpecialiste` reste dans la vue
            }
        }

        panel.revalidate();
        panel.repaint();
    }
}
