package org.chem.Vue;

import javax.swing.*;
import java.awt.*;

import org.chem.Modele.*;

/**
 * Fenêtre de l'interface d'administration permettant de gérer les patients et les spécialistes.
 * Cette fenêtre contient des onglets permettant d'accéder aux différentes sections de gestion des patients et des spécialistes.
 */
public class AdminVue extends BaseFrame {

    /** Onglets de la fenêtre pour naviguer entre les différentes sections. */
    private JTabbedPane onglets;

    /** Panel pour gérer les patients. */
    private JPanel gestionPatientsPanel;

    /** Panel pour gérer les spécialistes. */
    private JPanel gestionSpecialistesPanel;

    /**
     * Constructeur permettant d'initialiser la vue d'administration avec les onglets de gestion des patients et des spécialistes.
     *
     * @param admin L'utilisateur administrateur connecté, utilisé pour initialiser la fenêtre.
     */
    public AdminVue(Utilisateur admin) {
        super(admin);

        // Initialisation des onglets
        onglets = new JTabbedPane();

        // Onglet pour gérer les patients
        gestionPatientsPanel = new JPanel(new BorderLayout());
        onglets.addTab("Gérer les patients", gestionPatientsPanel);

        // Onglet pour gérer les spécialistes
        gestionSpecialistesPanel = new JPanel(new BorderLayout());
        onglets.addTab("Gérer les spécialistes", gestionSpecialistesPanel);

        // Ajout des onglets au panneau central de la fenêtre
        getCenterPanel().add(onglets, BorderLayout.CENTER);

        // Rendre la fenêtre visible
        setVisible(true);
    }

    /**
     * Récupère le panneau de gestion des patients.
     *
     * @return Le panneau de gestion des patients.
     */
    public JPanel getGestionPatientsPanel() { return gestionPatientsPanel; }

    /**
     * Récupère le panneau de gestion des spécialistes.
     *
     * @return Le panneau de gestion des spécialistes.
     */
    public JPanel getGestionSpecialistesPanel() { return gestionSpecialistesPanel; }
}
