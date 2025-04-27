package org.chem.Vue;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

import static org.chem.Modele.TypeUtilisateur.*;

/**
 * Vue de la fenêtre permettant à l'utilisateur de choisir son type de connexion.
 * Cette fenêtre présente trois boutons : Patient, Spécialiste et Administrateur.
 * Chaque bouton est accompagné d'une image correspondant au type d'utilisateur.
 */
public class ChoixConnexion extends BaseFrame {

    /** Le bouton pour la connexion en tant que Patient. */
    private JButton patientButton;

    /** Le bouton pour la connexion en tant que Spécialiste. */
    private JButton specialisteButton;

    /** Le bouton pour la connexion en tant qu'Administrateur. */
    private JButton adminButton;

    /**
     * Constructeur de la vue ChoixConnexion.
     * Initialise les composants graphiques et les positionne sur la fenêtre.
     * Affiche les boutons pour sélectionner le type d'utilisateur (Patient, Spécialiste, Administrateur).
     */
    public ChoixConnexion() {
        super();

        // Récupération du panneau central de la fenêtre
        JPanel contenuPanel = getCenterPanel();

        // Création du titre de la fenêtre
        JLabel titreLabel = new JLabel("Connexion", SwingConstants.CENTER);
        titreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titreLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        contenuPanel.add(titreLabel, BorderLayout.NORTH);

        // Création du panneau principal pour les boutons
        JPanel casePanel = new JPanel(new GridBagLayout());
        contenuPanel.add(casePanel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Ajout des boutons pour chaque type d'utilisateur avec leurs icônes respectives
        gbc.gridx = 0; gbc.gridy = 0;
        casePanel.add(createImageLabel(PATIENT.getImagePath()), gbc);
        gbc.gridx = 1;
        patientButton = createButton(PATIENT.getLabel());
        casePanel.add(patientButton, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        casePanel.add(createImageLabel(SPECIALISTE.getImagePath()), gbc);
        gbc.gridx = 1;
        specialisteButton = createButton(SPECIALISTE.getLabel());
        casePanel.add(specialisteButton, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        casePanel.add(createImageLabel(ADMIN.getImagePath()), gbc);
        gbc.gridx = 1;
        adminButton = createButton(ADMIN.getLabel());
        casePanel.add(adminButton, gbc);

        // Affichage de la fenêtre
        setVisible(true);
    }

    /**
     * Crée un JLabel contenant une image redimensionnée à partir du chemin spécifié.
     *
     * @param imagePath Le chemin de l'image à afficher.
     * @return Un JLabel avec l'image redimensionnée.
     */
    private JLabel createImageLabel(String imagePath) {
        JLabel label = new JLabel();
        try {
            URL imageUrl = getClass().getResource(imagePath);

            if (imageUrl != null) {
                ImageIcon icon = new ImageIcon(imageUrl);
                Image img = icon.getImage();
                Image resized = img.getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                label.setIcon(new ImageIcon(resized));
            } else {
                System.err.println("Image non trouvée : " + imagePath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return label;
    }

    /**
     * Crée un bouton avec le texte spécifié.
     *
     * @param text Le texte du bouton.
     * @return Le bouton créé.
     */
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(150, 75));
        return button;
    }

    /**
     * Récupère le bouton pour la connexion en tant que Patient.
     *
     * @return Le bouton pour la connexion en tant que Patient.
     */
    public JButton getPatientButton() {
        return patientButton;
    }

    /**
     * Récupère le bouton pour la connexion en tant que Spécialiste.
     *
     * @return Le bouton pour la connexion en tant que Spécialiste.
     */
    public JButton getSpecialisteButton() {
        return specialisteButton;
    }

    /**
     * Récupère le bouton pour la connexion en tant qu'Administrateur.
     *
     * @return Le bouton pour la connexion en tant qu'Administrateur.
     */
    public JButton getAdminButton() {
        return adminButton;
    }
}
