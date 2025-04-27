package org.chem.Vue;

import java.awt.*;
import javax.swing.*;

/**
 * Panneau représentant l'interface de prise de rendez-vous.
 * Permet de naviguer entre les mois et d'afficher un calendrier de rendez-vous.
 * Offre des boutons pour naviguer entre les mois ainsi qu'un panneau pour afficher les rendez-vous du mois sélectionné.
 */
public class PriseRDVVue extends JPanel {

    /** Bouton pour naviguer vers le mois suivant. */
    private JButton btnSuiv;

    /** Bouton pour naviguer vers le mois précédent. */
    private JButton btnPrec;

    /** Panneau contenant le calendrier des rendez-vous du mois sélectionné. */
    private JPanel gridPanel;

    /** Label affichant le mois actuel. */
    private JLabel moisLabel;

    /**
     * Constructeur initialisant l'interface de prise de rendez-vous.
     * Crée les boutons pour naviguer entre les mois et le panneau pour afficher le calendrier.
     */
    public PriseRDVVue() {
        setLayout(new BorderLayout());

        // Initialisation des composants
        btnSuiv = new JButton("→ suiv");
        btnPrec = new JButton("← pred");
        moisLabel = new JLabel("", SwingConstants.CENTER);

        // Création de l'en-tête avec les boutons de navigation et le label du mois
        JPanel header = new JPanel(new BorderLayout());
        header.add(btnPrec, BorderLayout.WEST);
        header.add(moisLabel, BorderLayout.CENTER);
        header.add(btnSuiv, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Création du panneau central pour afficher le calendrier des rendez-vous
        gridPanel = new JPanel();
        add(gridPanel, BorderLayout.CENTER);
    }

    /**
     * Met à jour le label affichant le mois actuel.
     *
     * @param texte Le texte à afficher sur le label du mois.
     */
    public void setMoisLabel(String texte) {
        moisLabel.setText(texte);
    }

    /**
     * Met à jour le panneau central avec un nouveau calendrier (panneau).
     *
     * @param panel Le panneau contenant le calendrier des rendez-vous.
     */
    public void setGridPanel(JPanel panel) {
        gridPanel.removeAll(); // Rafraîchit le panneau
        gridPanel.add(panel); // Ajoute le nouveau panneau
        gridPanel.revalidate(); // Revalide le panneau
        gridPanel.repaint(); // Repeint le panneau
    }

    /**
     * Récupère le bouton pour naviguer vers le mois suivant.
     *
     * @return Le bouton pour le mois suivant.
     */
    public JButton getBtnSuiv() {
        return btnSuiv;
    }

    /**
     * Récupère le bouton pour naviguer vers le mois précédent.
     *
     * @return Le bouton pour le mois précédent.
     */
    public JButton getBtnPrec() {
        return btnPrec;
    }
}
