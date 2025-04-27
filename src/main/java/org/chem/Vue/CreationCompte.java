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
    private JTextField SpecialiteField;

    /** Champ de texte pour le lieu (visible uniquement si l'utilisateur est un spécialiste). */
    private JTextField LieuField;

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
     *
     * @param typeUtilisateur Le type d'utilisateur pour la création du compte (ex: "patient", "specialiste").
     */
    public CreationCompte(String typeUtilisateur) {
        super();
        this.typeUtilisateur = typeUtilisateur;
        JPanel Contenu = getCenterPanel();
        JPanel boutonsPanel = new JPanel(new BorderLayout());

        // Création du titre de la fenêtre
        JLabel titreLabel = new JLabel("Création de Compte " + typeUtilisateur, SwingConstants.CENTER);
        titreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titreLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        Contenu.add(titreLabel, BorderLayout.NORTH);

        // Panneau principal pour les champs de saisie
        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        boutonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Création des champs de saisie
        nomField = new JTextField(10);
        prenomField = new JTextField(10);
        emailField = new JTextField(10);
        mdpField = new JPasswordField(10);
        SpecialiteField = new JTextField(10);
        LieuField = new JTextField(10);

        // Ajout des éléments à la fenêtre
        panel.add(new JLabel("Nom :"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom :"));
        panel.add(prenomField);
        panel.add(new JLabel("Email :"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(mdpField);

        // Si l'utilisateur est un spécialiste, on ajoute les champs spécialité et lieu
        if (typeUtilisateur.equalsIgnoreCase("specialiste")) {
            panel.add(new JLabel("Spécialité :"));
            panel.add(SpecialiteField);
            panel.add(new JLabel("Lieu :"));
            panel.add(LieuField);
        }

        // Création des boutons
        creerBtn = new JButton("Créer le compte");
        Font bigFont = new Font("Arial", Font.BOLD, 20);
        Dimension bigSize = new Dimension(250, 60);

        creerBtn.setFont(bigFont);
        btnRetour = new JButton("Retour");
        btnRetour.setFont(bigFont);

        creerBtn.setPreferredSize(bigSize);
        btnRetour.setPreferredSize(bigSize);

        // Ajout des boutons au panneau
        boutonsPanel.add(btnRetour, BorderLayout.WEST);
        boutonsPanel.add(creerBtn, BorderLayout.EAST);

        // Ajout du panneau principal et du panneau des boutons à la fenêtre
        Contenu.add(panel, BorderLayout.CENTER);
        Contenu.add(boutonsPanel, BorderLayout.SOUTH);

        // Affichage de la fenêtre
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
    public JTextField getSpecialiteField() { return SpecialiteField; }

    /**
     * Récupère le champ de texte pour le lieu (visible si l'utilisateur est un spécialiste).
     *
     * @return Le champ de texte du lieu.
     */
    public JTextField getLieuField() { return LieuField; }

    /**
     * Récupère le champ de sélection pour le type de patient (visible uniquement si l'utilisateur est un patient).
     *
     * @return Le champ de sélection du type de patient.
     */
    public JComboBox<String> getTypePatientBox() { return typePatientBox; }

    /**
     * Récupère le type d'utilisateur (ex: "patient", "specialiste").
     *
     * @return Le type d'utilisateur.
     */
    public String getTypeUtilisateur() { return typeUtilisateur; }

    /**
     * Récupère le bouton de retour.
     *
     * @return Le bouton de retour.
     */
    public JButton getBtnRetour() { return btnRetour; }

    /**
     * Récupère le bouton pour créer le compte.
     *
     * @return Le bouton pour créer le compte.
     */
    public JButton getCreerBtn() { return creerBtn; }
}
