package Controleur;

import Dao.*;
import Modele.*;
import Vue.*;

import javax.swing.*;
import java.awt.*;
import java.sql.Time;
import java.util.ArrayList;

public class RechercheControleur {
    private Recherche vue;
    private UtilisateurDAOImpl specialisteDAO;

    public RechercheControleur(Recherche vue) {
        this.vue = vue;
        DatabaseConnection db = DatabaseConnection.getInstance("rdv_specialiste", "root", "root");
        this.specialisteDAO = new UtilisateurDAOImpl(db);

        this.vue.getRechercherBtn().addActionListener(e -> rechercher());
    }

    private void rechercher() {
        String motCle = vue.getMotCleField().getText().trim();
        String lieu = vue.getLieuField().getText().trim();
        String jour = null;
        Time heure = null;

        if (vue.getJourCombo().getSelectedItem() != null && !vue.getJourCombo().getSelectedItem().toString().isEmpty()) {
            jour = vue.getJourCombo().getSelectedItem().toString().toLowerCase();
        }

        if (vue.getHeureCombo().getSelectedItem() != null && !vue.getHeureCombo().getSelectedItem().toString().isEmpty()) {
            try {
                heure = Time.valueOf(vue.getHeureCombo().getSelectedItem().toString() + ":00");
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(vue, "Heure invalide (ex : 09:00)");
                return;
            }
        }

        if (motCle.isEmpty() || motCle.equalsIgnoreCase("Nom, spécialité")) {
            JOptionPane.showMessageDialog(vue, "Saisir un nom ou une spécialité", "Champ requis", JOptionPane.WARNING_MESSAGE);
            return;
        }

        ArrayList<Specialiste> resultats = specialisteDAO.rechercherSpecialistes(motCle, jour, heure, lieu);
        afficherResultats(resultats);
    }

    private void afficherResultats(ArrayList<Specialiste> specialistes) {
        JPanel panel = vue.getResultatsPanel();
        panel.removeAll();

        if (specialistes.isEmpty()) {
            JLabel label = new JLabel("Aucun spécialiste trouvé.");
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            panel.add(label);
        } else {
            for (Specialiste s : specialistes) {
                panel.add(vue.creerPanelSpecialiste(s)); // si tu veux que `creerPanelSpecialiste` reste dans la vue
            }
        }

        panel.revalidate();
        panel.repaint();
    }
}
