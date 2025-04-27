package org.chem.Vue;

import javax.swing.*;
import java.awt.*;

/**
 * Vue de l'interface d'administration permettant de gérer les spécialistes.
 * Elle permet de rechercher des spécialistes, d'afficher la liste des spécialistes et d'ajouter de nouveaux spécialistes.
 */
public class AdminSpecialisteVue extends JPanel {

    /** Champ de texte pour la recherche de spécialistes. */
    private JTextField rechercheField;

    /** Boutons pour rechercher un spécialiste et ajouter un nouveau spécialiste. */
    private JButton rechercherBtn, ajouterBtn;

    /** Panel affichant la liste des spécialistes. */
    private JPanel listeSpecialistesPanel;

    /**
     * Constructeur initialisant l'interface d'administration des spécialistes.
     * L'interface comprend un champ de recherche, un bouton pour rechercher des spécialistes,
     * un bouton pour ajouter un nouveau spécialiste et un panel pour afficher la liste des spécialistes.
     */
    public AdminSpecialisteVue() {
        setLayout(new BorderLayout());

        // Haut : barre de recherche + bouton ajouter
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rechercheField = new JTextField(20);
        rechercherBtn = new JButton("Rechercher");
        ajouterBtn = new JButton("Ajouter un spécialiste");

        topPanel.add(new JLabel("Spécialité ou nom :"));
        topPanel.add(rechercheField);
        topPanel.add(rechercherBtn);
        topPanel.add(ajouterBtn);

        add(topPanel, BorderLayout.NORTH);

        // Centre : liste scrollable des spécialistes
        listeSpecialistesPanel = new JPanel();
        listeSpecialistesPanel.setLayout(new BoxLayout(listeSpecialistesPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(listeSpecialistesPanel);

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Crée un panel pour choisir un jour et une heure pour un créneau horaire.
     * Ce panel est utilisé lors de la gestion des horaires des spécialistes.
     *
     * @param jourCombo Le JComboBox pour choisir un jour de la semaine.
     * @param heureCombo Le JComboBox pour choisir une heure.
     * @return Un JPanel contenant les options de sélection de jour et d'heure.
     */
    public JPanel creerPanelChoixHoraire(JComboBox<String> jourCombo, JComboBox<String> heureCombo) {
        String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
        String[] horaires = {"08:00", "09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00"};

        // Ajout des jours et horaires aux JComboBox
        for (String jour : jours) jourCombo.addItem(jour);
        for (String h : horaires) heureCombo.addItem(h);

        // Création et retour du panel de sélection
        JPanel panelChoix = new JPanel(new GridLayout(2, 2));
        panelChoix.add(new JLabel("Jour de la semaine :"));
        panelChoix.add(jourCombo);
        panelChoix.add(new JLabel("Heure de début :"));
        panelChoix.add(heureCombo);

        return panelChoix;
    }

    /**
     * Crée un panel permettant de saisir les informations d'un spécialiste lors de la création ou modification de son profil.
     *
     * @param Nom Champ de texte pour le nom du spécialiste.
     * @param Prenom Champ de texte pour le prénom du spécialiste.
     * @param email Champ de texte pour l'email du spécialiste.
     * @param mdp Champ de texte pour le mot de passe du spécialiste.
     * @param Specialite Champ de texte pour la spécialité du spécialiste.
     * @param Lieu Champ de texte pour le lieu d'exercice du spécialiste.
     * @return Un JPanel contenant les champs d'information pour le spécialiste.
     */
    public JPanel creerPanelInfoSpecialiste(JTextField Nom, JTextField Prenom, JTextField email, JPasswordField mdp, JTextField Specialite, JTextField Lieu) {
        JPanel panelInfoSpecialiste = new JPanel();
        panelInfoSpecialiste.setLayout(new GridLayout(6, 2, 10, 10)); // marges (hgap, vgap)

        panelInfoSpecialiste.add(new JLabel("Nom"));
        panelInfoSpecialiste.add(Nom);
        panelInfoSpecialiste.add(new JLabel("Prenom"));
        panelInfoSpecialiste.add(Prenom);
        panelInfoSpecialiste.add(new JLabel("Email"));
        panelInfoSpecialiste.add(email);
        panelInfoSpecialiste.add(new JLabel("Mot de passe"));
        panelInfoSpecialiste.add(mdp);
        panelInfoSpecialiste.add(new JLabel("Specialite"));
        panelInfoSpecialiste.add(Specialite);
        panelInfoSpecialiste.add(new JLabel("Lieu"));
        panelInfoSpecialiste.add(Lieu);

        return panelInfoSpecialiste;
    }

    // Getters pour que le contrôleur y accède

    /**
     * Récupère le champ de texte de recherche des spécialistes.
     *
     * @return Le champ de texte de recherche.
     */
    public JTextField getRechercheField() { return rechercheField; }

    /**
     * Récupère le bouton de recherche des spécialistes.
     *
     * @return Le bouton de recherche.
     */
    public JButton getRechercherBtn() { return rechercherBtn; }

    /**
     * Récupère le bouton d'ajout d'un nouveau spécialiste.
     *
     * @return Le bouton d'ajout.
     */
    public JButton getAjouterBtn() { return ajouterBtn; }

    /**
     * Récupère le panneau contenant la liste des spécialistes.
     *
     * @return Le panneau de liste des spécialistes.
     */
    public JPanel getListeSpecialistesPanel() { return listeSpecialistesPanel; }
}
