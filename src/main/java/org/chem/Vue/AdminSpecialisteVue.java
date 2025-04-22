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

    // Getters pour que le contrôleur y accède
    public JTextField getRechercheField() { return rechercheField; }
    public JButton getRechercherBtn() { return rechercherBtn; }
    public JButton getAjouterBtn() { return ajouterBtn; }
    public JPanel getListeSpecialistesPanel() { return listeSpecialistesPanel; }
}
