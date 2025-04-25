package org.chem.Vue;

import javax.swing.*;
import java.awt.*;

public class AdminSpecialisteVue extends JPanel {

    private JTextField rechercheField;
    private JButton rechercherBtn, ajouterBtn;
    private JPanel listeSpecialistesPanel;

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

    public JPanel creerPanelChoixHoraire(JComboBox<String> jourCombo, JComboBox<String> heureCombo) {
        String[] jours = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi"};
        String[] horaires = {"08:00", "09:00", "10:00", "11:00", "13:00", "14:00", "15:00", "16:00"};

        for (String jour : jours) jourCombo.addItem(jour);
        for (String h : horaires) heureCombo.addItem(h);

        JPanel panelChoix = new JPanel(new GridLayout(2, 2));
        panelChoix.add(new JLabel("Jour de la semaine :"));
        panelChoix.add(jourCombo);
        panelChoix.add(new JLabel("Heure de début :"));
        panelChoix.add(heureCombo);

        return panelChoix;
    }

    public JPanel creerPanelInfoSpecialiste(JTextField Nom, JTextField Prenom, JTextField email,JPasswordField mdp, JTextField Specialite, JTextField Lieu) {

        JPanel panelInfoSpecialiste = new JPanel();
        panelInfoSpecialiste.setLayout(new GridLayout(6, 2, 10, 10)); // marges (hgap, vgap)        panelInfoSpecialiste.add(new JLabel("Nom"));
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
    public JTextField getRechercheField() { return rechercheField; }
    public JButton getRechercherBtn() { return rechercherBtn; }
    public JButton getAjouterBtn() { return ajouterBtn; }
    public JPanel getListeSpecialistesPanel() { return listeSpecialistesPanel; }


}
