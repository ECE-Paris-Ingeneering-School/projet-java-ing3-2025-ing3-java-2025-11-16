package org.chem.Vue;

import javax.swing.*;
import java.awt.*;

import org.chem.Modele.*;

public class AdminVue extends BaseFrame {

    private JTabbedPane onglets;

    private JPanel gestionPatientsPanel;
    private JPanel gestionSpecialistesPanel;
    private JPanel gestionRendezVousPanel;
    private JPanel historiquePatientsPanel;

    public AdminVue(Utilisateur admin) {
        super(admin);

        onglets = new JTabbedPane();

        // Onglet pour gérer les patients
        gestionPatientsPanel = new JPanel(new BorderLayout());
        onglets.addTab("Gérer les patients", gestionPatientsPanel);

        // Onglet pour gérer les spécialistes
        gestionSpecialistesPanel = new JPanel(new BorderLayout());
        onglets.addTab("Gérer les specialistes", gestionSpecialistesPanel);

        // Onglet pour les RDV
        gestionRendezVousPanel = new JPanel(new BorderLayout());
        onglets.addTab("Ajouter des Rendez-vous", gestionRendezVousPanel);

        getCenterPanel().add(onglets, BorderLayout.CENTER);

        setVisible(true);
    }

    // Getters pour chaque panel si besoin dans les contrôleurs
    public JPanel getGestionPatientsPanel() { return gestionPatientsPanel; }
    public JPanel getGestionSpecialistesPanel() { return gestionSpecialistesPanel; }
    public JPanel getGestionRendezVousPanel() { return gestionRendezVousPanel; }
}
