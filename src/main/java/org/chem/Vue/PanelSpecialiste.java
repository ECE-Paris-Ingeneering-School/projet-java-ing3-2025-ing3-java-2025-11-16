package org.chem.Vue;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import org.chem.Modele.*;

/**
 * Panneau représentant un spécialiste avec son emploi du temps et ses actions.
 * Permet d'afficher les informations d'un spécialiste et de gérer les actions comme la modification
 * ou la suppression du spécialiste et la gestion des horaires.
 */
public class PanelSpecialiste extends JPanel {

    /** Spécialiste associé à ce panneau. */
    private final Specialiste specialiste;

    /** Bouton pour modifier les informations du spécialiste. */
    private final JButton btnSupprimerSpecialiste;

    /** Bouton pour supprimer un spécialiste. */
    private final JButton btnModifierSpecialiste;

    /** Bouton pour ajouter un horaire au spécialiste. */
    private final JButton btnAjouterHoraire;

    /** Panneau affichant l'emploi du temps du spécialiste. */
    private final JPanel edtPanel;

    /** Panneau affichant les rendez-vous associés au spécialiste. */
    private final JPanel RdvPanel;

    /**
     * Constructeur créant un panneau pour afficher un spécialiste avec ses informations, ses actions possibles
     * et son emploi du temps. Les rendez-vous sont également affichés.
     *
     * @param s Le spécialiste à afficher.
     * @param listRdv Liste des rendez-vous à afficher pour ce spécialiste.
     */
    public PanelSpecialiste(Specialiste s, ArrayList<RendezVous> listRdv) {
        this.specialiste = s;

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createLineBorder(Color.GRAY)); // Bordure grise
        setBackground(new Color(240, 248, 255)); // Couleur de fond
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 250)); // Taille maximale du panneau

        // Création du label pour afficher le nom et la spécialisation du spécialiste
        JLabel nomLabel = new JLabel(s.getPrenom() + " " + s.getNom() + " - " + s.getSpecialisation());
        nomLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Panneau contenant les informations du spécialiste
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setOpaque(false);
        infoPanel.add(nomLabel);

        // Création des boutons d'actions
        btnModifierSpecialiste = new JButton("Modifier le spécialiste");
        btnSupprimerSpecialiste = new JButton("Supprimer le spécialiste");
        btnAjouterHoraire = new JButton("Ajouter un horaire");

        // Panneau des actions
        JPanel actionsPanel = new JPanel();
        actionsPanel.setOpaque(false);
        actionsPanel.add(btnAjouterHoraire);
        actionsPanel.add(btnModifierSpecialiste);
        actionsPanel.add(btnSupprimerSpecialiste);

        // Emploi du temps
        edtPanel = new JPanel();
        edtPanel.setLayout(new BoxLayout(edtPanel, BoxLayout.Y_AXIS));
        edtPanel.setOpaque(false);

        // Affichage des horaires
        ArrayList<Horaire> edt = s.getEmploiDuTemps();
        if (edt.isEmpty()) {
            edtPanel.add(new JLabel("Aucun horaire défini."));
        } else {
            for (Horaire h : edt) {
                JPanel ligne = new JPanel(new FlowLayout(FlowLayout.LEFT));
                ligne.setOpaque(false);

                String texte = Horaire.convertirJourIntEnString(h.getJourSemaine()) + " : " + h.getHeureDebut() + " - " + h.getHeureFin();
                JLabel label = new JLabel(texte);

                // Boutons de modification et de suppression d'horaire
                JButton btnModiferHoraire = new JButton("Modifier");
                btnModiferHoraire.setActionCommand("modifier_" + h.getId());

                JButton btnSupprimerHoraire = new JButton("Supprimer");
                btnSupprimerHoraire.setActionCommand("supprimer_" + h.getId());

                ligne.add(label);
                ligne.add(btnModiferHoraire);
                ligne.add(btnSupprimerHoraire);

                edtPanel.add(ligne);
            }
        }

        // Panneau des rendez-vous
        RdvPanel = new JPanel(new GridLayout(0, 1));
        RdvPanel.setOpaque(false);

        // Ajout des panneaux au panneau principal
        add(infoPanel, BorderLayout.NORTH);
        add(edtPanel, BorderLayout.WEST);
        add(actionsPanel, BorderLayout.SOUTH);
        add(RdvPanel, BorderLayout.EAST);
    }

    /**
     * Affiche la liste des rendez-vous formatés pour ce spécialiste.
     * Cette méthode rafraîchit le panneau des rendez-vous et affiche les nouveaux rendez-vous.
     *
     * @param rdvFormates Liste des rendez-vous formatés sous forme de chaîne de caractères.
     */
    public void afficherRendezVous(ArrayList<String> rdvFormates) {
        RdvPanel.removeAll(); // Rafraîchissement des rendez-vous précédents
        for (String texte : rdvFormates) {
            RdvPanel.add(new JLabel("• " + texte)); // Ajout d'un label pour chaque rendez-vous
        }
        RdvPanel.revalidate(); // Revalidation du panneau
        RdvPanel.repaint(); // Rafraîchissement du panneau
    }

    /**
     * Récupère le bouton permettant de modifier le spécialiste.
     *
     * @return Le bouton de modification.
     */
    public JButton getBtnModifierSpecialiste() {
        return btnModifierSpecialiste;
    }

    /**
     * Récupère le bouton permettant de supprimer le spécialiste.
     *
     * @return Le bouton de suppression.
     */
    public JButton getBtnSupprimerSpecialiste() {
        return btnSupprimerSpecialiste;
    }

    /**
     * Récupère le bouton permettant d'ajouter un horaire pour le spécialiste.
     *
     * @return Le bouton d'ajout d'horaire.
     */
    public JButton getBtnAjouterHoraire() {
        return btnAjouterHoraire;
    }

    /**
     * Récupère l'objet `Specialiste` associé à ce panneau.
     *
     * @return Le spécialiste associé à ce panneau.
     */
    public Specialiste getSpecialiste() {
        return specialiste;
    }

    /**
     * Récupère le panneau de l'emploi du temps du spécialiste.
     *
     * @return Le panneau de l'emploi du temps.
     */
    public JPanel getEmploiDuTempsPanel() {
        return edtPanel;
    }
}
