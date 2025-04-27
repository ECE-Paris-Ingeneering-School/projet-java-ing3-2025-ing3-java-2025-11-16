package org.chem.Vue;

import javax.swing.*;
import java.awt.*;

/**
 * Vue de l'interface d'administration permettant de gérer les patients.
 * Cette vue permet d'afficher une liste scrollable des patients.
 */
public class AdminPatientVue extends JPanel {

    /** Panel affichant la liste des patients. */
    private JPanel listePatientsPanel;

    /**
     * Constructeur initialisant l'interface d'administration des patients.
     * L'interface comprend un panneau scrollable affichant la liste des patients.
     */
    public AdminPatientVue() {
        setLayout(new BorderLayout());

        // Création du panel pour afficher la liste des patients
        listePatientsPanel = new JPanel();
        listePatientsPanel.setLayout(new BoxLayout(listePatientsPanel, BoxLayout.Y_AXIS));

        // Création d'un JScrollPane pour rendre le panel scrollable
        JScrollPane scrollPane = new JScrollPane(listePatientsPanel);

        // Ajout du JScrollPane au panel principal
        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Récupère le panneau contenant la liste des patients.
     *
     * @return Le panneau de liste des patients.
     */
    public JPanel getListePatientsPanel() { return listePatientsPanel; }
}
