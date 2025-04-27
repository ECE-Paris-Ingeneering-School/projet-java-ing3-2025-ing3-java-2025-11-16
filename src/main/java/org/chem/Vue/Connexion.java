package org.chem.Vue;

import javax.swing.*;
import java.awt.*;

/**
 * Fenêtre permettant à un utilisateur de se connecter à l'application en fonction de son type.
 * Cette fenêtre contient des champs pour l'email et le mot de passe ainsi que des boutons
 * pour se connecter, s'inscrire, ou revenir à l'écran précédent.
 */
public class Connexion extends BaseFrame {

    /** Le champ de texte pour saisir l'email. */
    private JTextField emailField;

    /** Le champ de texte pour saisir le mot de passe. */
    private JPasswordField passwordField;

    /** Le type d'utilisateur qui essaie de se connecter (ex: Patient, Spécialiste). */
    private String typeUtilisateur;

    /** Le bouton pour revenir à l'écran précédent. */
    private JButton btnRetour;

    /** Le bouton pour soumettre la connexion. */
    private JButton loginBtn;

    /** Le bouton pour s'inscrire (si l'utilisateur n'a pas encore de compte). */
    private JButton signupBtn;

    /**
     * Constructeur pour initialiser la vue de connexion avec le type d'utilisateur.
     *
     * @param typeUtilisateur Le type d'utilisateur (ex: "Patient", "Spécialiste").
     */
    public Connexion(String typeUtilisateur) {
        super();
        this.typeUtilisateur = typeUtilisateur;
        initConnection();
    }

    /**
     * Constructeur par défaut qui définit "Utilisateur" comme type d'utilisateur.
     */
    public Connexion() {
        this("Utilisateur");
    }

    /**
     * Constructeur qui initialise la connexion avec un login et un mot de passe spécifiques.
     *
     * @param login Le login de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     */
    public Connexion(String login, String password) {
        this("Utilisateur");
        setLogin(login);
        setPassword(password);
    }

    /**
     * Définit le mot de passe dans le champ de texte.
     *
     * @param password Le mot de passe à afficher dans le champ.
     */
    public void setPassword(String password) {
        passwordField.setText(password);
    }

    /**
     * Définit l'email dans le champ de texte.
     *
     * @param login L'email de l'utilisateur à afficher dans le champ.
     */
    public void setLogin(String login) {
        emailField.setText(login);
    }

    /**
     * Initialise les composants graphiques pour la fenêtre de connexion.
     * Crée les champs de texte, les boutons et les place dans la fenêtre.
     */
    private void initConnection() {
        JPanel contenu = getCenterPanel();
        JPanel boutonPanel = new JPanel(new BorderLayout());

        // Création du titre de la fenêtre
        JLabel titreLabel = new JLabel("Connexion " + typeUtilisateur, SwingConstants.CENTER);
        titreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titreLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        contenu.add(titreLabel, BorderLayout.NORTH);

        // Panneau principal pour les champs de saisie
        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        boutonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        emailField = new JTextField(10);
        passwordField = new JPasswordField(10);

        // Ajout des éléments dans le panneau
        panel.add(new JLabel("Email :"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(passwordField);

        // Création des boutons
        loginBtn = new JButton("Se connecter");
        signupBtn = new JButton("Pas de compte?");
        btnRetour = new JButton("Retour");

        // Définition de la police et de la taille des boutons
        Font bigFont = new Font("Arial", Font.BOLD, 18);
        Dimension bigSize = new Dimension(200, 30);
        Dimension size2 = new Dimension(150, 20);

        loginBtn.setFont(bigFont);
        signupBtn.setFont(bigFont);
        btnRetour.setFont(bigFont);

        loginBtn.setPreferredSize(bigSize);
        signupBtn.setPreferredSize(size2);
        btnRetour.setPreferredSize(bigSize);

        // Ajout des boutons au panneau de boutons
        boutonPanel.add(loginBtn, BorderLayout.EAST);
        boutonPanel.add(signupBtn, BorderLayout.CENTER);
        boutonPanel.add(btnRetour, BorderLayout.WEST);

        // Ajout des panneaux à la fenêtre
        contenu.add(boutonPanel, BorderLayout.SOUTH);
        contenu.add(panel, BorderLayout.CENTER);

        // Affichage de la fenêtre
        setVisible(true);
    }

    // ** GETTERS pour le contrôleur **

    /**
     * Récupère le bouton de connexion.
     *
     * @return Le bouton de connexion.
     */
    public JButton getLoginBtn() {
        return loginBtn;
    }

    /**
     * Récupère le bouton pour s'inscrire (si l'utilisateur n'a pas de compte).
     *
     * @return Le bouton d'inscription.
     */
    public JButton getSignupBtn() {
        return signupBtn;
    }

    /**
     * Récupère le bouton pour revenir à l'écran précédent.
     *
     * @return Le bouton de retour.
     */
    public JButton getBtnRetour() {
        return btnRetour;
    }

    /**
     * Récupère l'email saisi dans le champ de texte.
     *
     * @return L'email de l'utilisateur.
     */
    public String getEmail() {
        return emailField.getText().trim();
    }

    /**
     * Récupère le mot de passe saisi dans le champ de texte.
     *
     * @return Le mot de passe de l'utilisateur.
     */
    public String getMotDePasse() {
        return new String(passwordField.getPassword()).trim();
    }

    /**
     * Récupère le type d'utilisateur (Patient, Spécialiste, etc.) pour cette connexion.
     *
     * @return Le type d'utilisateur.
     */
    public String getTypeUtilisateur() {
        return typeUtilisateur;
    }
}
