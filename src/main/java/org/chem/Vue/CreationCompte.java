package org.chem.Vue;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre permettant à un utilisateur de créer un compte dans l'application en fonction de son type d'utilisateur.
 * Cette fenêtre contient des champs de saisie pour les informations personnelles (nom, prénom, email, mot de passe),
 * ainsi que des champs supplémentaires pour un spécialiste (spécialité, lieu) et un bouton pour créer le compte.
 */
public class CreationCompte extends BaseFrame {

    /** Champ de texte pour le nom de l'utilisateur. */
    private JTextField nomField;
    /** Champ de texte pour le prénom de l'utilisateur. */
    private JTextField prenomField;
    /** Champ de texte pour l'email de l'utilisateur. */
    private JTextField emailField;
    /** Champ de texte pour la spécialité (visible uniquement si l'utilisateur est un spécialiste). */
    private JTextField specialiteField;
    /** Champ de texte pour le lieu (visible uniquement si l'utilisateur est un spécialiste). */
    private JTextField lieuField;
    /** Champ de texte pour le mot de passe de l'utilisateur. */
    private JPasswordField mdpField;
    /** Champ de sélection pour le type de patient (visible uniquement si l'utilisateur est un patient). */
    private JComboBox<String> typePatientBox;
    /** Type d'utilisateur (ex: "patient", "specialiste"). */
    private String typeUtilisateur;

    /** Bouton permettant de revenir à l'écran précédent. */
    private JButton btnRetour;
    /** Bouton permettant de créer le compte. */
    private JButton creerBtn;

    /**
     * Constructeur permettant d'initialiser la fenêtre de création de compte en fonction du type d'utilisateur.
     * @param typeUtilisateur Le type d'utilisateur pour la création du compte (ex: "patient", "specialiste").
     */
    public CreationCompte(String typeUtilisateur) {
        super();
        this.typeUtilisateur = typeUtilisateur;
        initCreationCompte();
    }

    private void initCreationCompte() {
        JPanel contenu = getCenterPanel();
        contenu.setLayout(new GridBagLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        formPanel.setMaximumSize(new Dimension(800, 700));

        // Titre
        JLabel titreLabel = new JLabel("Création de compte ", SwingConstants.CENTER);
        titreLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titreLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        formPanel.add(titreLabel);

        // Nom
        JPanel nomPanel = new JPanel(new BorderLayout());
        nomPanel.setBackground(Color.WHITE);
        nomPanel.setMaximumSize(new Dimension(500, 50));
        nomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nomLabel = new JLabel("Nom :");
        nomLabel.setFont(new Font("Arial", Font.BOLD, 15));
        nomLabel.setHorizontalAlignment(SwingConstants.LEFT);
        nomPanel.add(nomLabel, BorderLayout.NORTH);

        nomField = new JTextField(40);
        nomField.setMaximumSize(new Dimension(500, 50));
        nomField.setFont(new Font("Arial", Font.PLAIN, 16));
        nomPanel.add(nomField, BorderLayout.CENTER);

        formPanel.add(nomPanel);
        formPanel.add(Box.createVerticalStrut(15));

        // Prénom
        JPanel prenomPanel = new JPanel(new BorderLayout());
        prenomPanel.setBackground(Color.WHITE);
        prenomPanel.setMaximumSize(new Dimension(500, 50));
        prenomPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel prenomLabel = new JLabel("Prénom :");
        prenomLabel.setFont(new Font("Arial", Font.BOLD, 15));
        prenomLabel.setHorizontalAlignment(SwingConstants.LEFT);
        prenomPanel.add(prenomLabel, BorderLayout.NORTH);

        prenomField = new JTextField(40);
        prenomField.setMaximumSize(new Dimension(500, 50));
        prenomField.setFont(new Font("Arial", Font.PLAIN, 16));
        prenomPanel.add(prenomField, BorderLayout.CENTER);

        formPanel.add(prenomPanel);
        formPanel.add(Box.createVerticalStrut(15));

        // Email
        JPanel emailPanel = new JPanel(new BorderLayout());
        emailPanel.setBackground(Color.WHITE);
        emailPanel.setMaximumSize(new Dimension(500, 50));
        emailPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel emailLabel = new JLabel("Email :");
        emailLabel.setFont(new Font("Arial", Font.BOLD, 15));
        emailLabel.setHorizontalAlignment(SwingConstants.LEFT);
        emailPanel.add(emailLabel, BorderLayout.NORTH);

        emailField = new JTextField(40);
        emailField.setMaximumSize(new Dimension(500, 50));
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailPanel.add(emailField, BorderLayout.CENTER);

        formPanel.add(emailPanel);
        formPanel.add(Box.createVerticalStrut(15));

        // Mot de passe
        JPanel mdpPanel = new JPanel(new BorderLayout());
        mdpPanel.setBackground(Color.WHITE);
        mdpPanel.setMaximumSize(new Dimension(500, 50));
        mdpPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel mdpLabel = new JLabel("Mot de passe :");
        mdpLabel.setFont(new Font("Arial", Font.BOLD, 15));
        mdpLabel.setHorizontalAlignment(SwingConstants.LEFT);
        mdpPanel.add(mdpLabel, BorderLayout.NORTH);

        mdpField = new JPasswordField(40);
        mdpField.setMaximumSize(new Dimension(500, 50));
        mdpField.setFont(new Font("Arial", Font.PLAIN, 16));
        mdpPanel.add(mdpField, BorderLayout.CENTER);

        formPanel.add(mdpPanel);
        formPanel.add(Box.createVerticalStrut(25));

        // Champs spécifiques pour spécialiste
        if (typeUtilisateur.equalsIgnoreCase("specialiste")) {
            // Spécialité
            JPanel specialitePanel = new JPanel(new BorderLayout());
            specialitePanel.setBackground(Color.WHITE);
            specialitePanel.setMaximumSize(new Dimension(500, 50));
            specialitePanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel specialiteLabel = new JLabel("Spécialité :");
            specialiteLabel.setFont(new Font("Arial", Font.BOLD, 15));
            specialiteLabel.setHorizontalAlignment(SwingConstants.LEFT);
            specialitePanel.add(specialiteLabel, BorderLayout.NORTH);

            specialiteField = new JTextField(40);
            specialiteField.setMaximumSize(new Dimension(500, 50));
            specialiteField.setFont(new Font("Arial", Font.PLAIN, 16));
            specialitePanel.add(specialiteField, BorderLayout.CENTER);

            formPanel.add(specialitePanel);
            formPanel.add(Box.createVerticalStrut(15));

            // Lieu
            JPanel lieuPanel = new JPanel(new BorderLayout());
            lieuPanel.setBackground(Color.WHITE);
            lieuPanel.setMaximumSize(new Dimension(500, 50));
            lieuPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JLabel lieuLabel = new JLabel("Lieu :");
            lieuLabel.setFont(new Font("Arial", Font.BOLD, 15));
            lieuLabel.setHorizontalAlignment(SwingConstants.LEFT);
            lieuPanel.add(lieuLabel, BorderLayout.NORTH);

            lieuField = new JTextField(40);
            lieuField.setMaximumSize(new Dimension(500, 50));
            lieuField.setFont(new Font("Arial", Font.PLAIN, 16));
            lieuPanel.add(lieuField, BorderLayout.CENTER);

            formPanel.add(lieuPanel);
            formPanel.add(Box.createVerticalStrut(15));
        }

        // Boutons
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonsPanel.setBackground(Color.WHITE);

        btnRetour = new JButton("Retour");
        btnRetour.setFont(new Font("Arial", Font.BOLD, 16));
        btnRetour.setPreferredSize(new Dimension(160, 45));
        btnRetour.setMaximumSize(new Dimension(200, 45));
        btnRetour.setBackground(Color.LIGHT_GRAY);
        btnRetour.setForeground(Color.WHITE);
        btnRetour.setFocusPainted(false);
        btnRetour.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        btnRetour.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonsPanel.add(btnRetour);

        buttonsPanel.add(Box.createHorizontalStrut(20));

        creerBtn = new JButton("Créer le compte");
        creerBtn.setFont(new Font("Arial", Font.BOLD, 16));
        creerBtn.setPreferredSize(new Dimension(160, 45));
        creerBtn.setMaximumSize(new Dimension(200, 45));
        creerBtn.setBackground(Color.decode("#649FCB"));
        creerBtn.setForeground(Color.WHITE);
        creerBtn.setFocusPainted(false);
        creerBtn.setBorder(BorderFactory.createLineBorder(Color.decode("#649FCB")));
        creerBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonsPanel.add(creerBtn);

        formPanel.add(buttonsPanel);

        // Centrage dans la page
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        contenu.add(formPanel, gbc);

        setVisible(true);
    }

    // ** Getters pour le contrôleur **

    /**
     * Récupère le champ de texte pour le nom de l'utilisateur.
     *
     * @return Le champ de texte du nom.
     */
    public JTextField getNomField() { return nomField; }

    /**
     * Récupère le champ de texte pour le prénom de l'utilisateur.
     *
     * @return Le champ de texte du prénom.
     */
    public JTextField getPrenomField() { return prenomField; }

    /**
     * Récupère le champ de texte pour l'email de l'utilisateur.
     *
     * @return Le champ de texte de l'email.
     */
    public JTextField getEmailField() { return emailField; }

    /**
     * Récupère le champ de texte pour le mot de passe de l'utilisateur.
     *
     * @return Le champ de texte du mot de passe.
     */
    public JPasswordField getMdpField() { return mdpField; }

    /**
     * Récupère le champ de texte pour la spécialité (visible si l'utilisateur est un spécialiste).
     *
     * @return Le champ de texte de la spécialité.
     */
    public JTextField getSpecialiteField() { return specialiteField; }

    /**
     * Récupère le champ de texte pour le lieu (visible si l'utilisateur est un spécialiste).
     * @return Le champ de texte du lieu.
     */
    public JTextField getLieuField() { return lieuField; }

    /**
     * Récupère le champ de sélection pour le type de patient (visible uniquement si l'utilisateur est un patient).
     * @return Le champ de sélection du type de patient.
     */
    public JComboBox<String> getTypePatientBox() { return typePatientBox; }

    /**
     * Récupère le type d'utilisateur (ex: "patient", "specialiste").
     * @return Le type d'utilisateur.
     */
    public String getTypeUtilisateur() { return typeUtilisateur; }

    /**
     * Récupère le bouton de retour.
     * @return Le bouton de retour.
     */
    public JButton getBtnRetour() { return btnRetour; }

    /**
     * Récupère le bouton pour créer le compte.
     * @return Le bouton pour créer le compte.
     */
    public JButton getCreerBtn() { return creerBtn; }
}
