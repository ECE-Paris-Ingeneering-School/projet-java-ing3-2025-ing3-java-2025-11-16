package org.chem.Vue;

import javax.swing.*;
import java.awt.*;

public class Connexion extends BaseFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    //private String typeUtilisateur;

    private JButton btnRetour;
    private JButton loginBtn;
    private JButton signupBtn;

    public Connexion(String typeUtilisateur) {
        super();
       // this.typeUtilisateur = typeUtilisateur;
        initConnection();
    }

    public Connexion() {
        super();
        initConnection();
    }

    public Connexion(String login, String password) {
        super();
        initConnection();
        emailField.setText(login);
        passwordField.setText(password);
    }


    private void initConnection() {
        JPanel contenu = getCenterPanel();
        JPanel boutonPanel = new JPanel(new BorderLayout());

        JLabel titreLabel = new JLabel("Connexion", SwingConstants.CENTER);
        titreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titreLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        contenu.add(titreLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        boutonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        emailField = new JTextField(10);
        passwordField = new JPasswordField(10);

        panel.add(new JLabel("Email :"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(passwordField);

        loginBtn = new JButton("Se connecter");
        signupBtn = new JButton("Pas de compte?");
        btnRetour = new JButton("Retour");

        Font bigFont = new Font("Arial", Font.BOLD, 18);
        Dimension bigSize = new Dimension(200, 30);
        Dimension size2 = new Dimension(150, 20);

        loginBtn.setFont(bigFont);
        signupBtn.setFont(bigFont);
        btnRetour.setFont(bigFont);

        loginBtn.setPreferredSize(bigSize);
        signupBtn.setPreferredSize(size2);
        btnRetour.setPreferredSize(bigSize);

        boutonPanel.add(loginBtn, BorderLayout.EAST);
        boutonPanel.add(signupBtn, BorderLayout.CENTER);
        boutonPanel.add(btnRetour, BorderLayout.WEST);

        contenu.add(boutonPanel, BorderLayout.SOUTH);
        contenu.add(panel, BorderLayout.CENTER);

        setVisible(true);
    }

    // GETTERS pour le contrôleur
    public JButton getLoginBtn() { return loginBtn; }
    public JButton getSignupBtn() { return signupBtn; }
    public JButton getBtnRetour() { return btnRetour; }

    public String getEmail() { return emailField.getText().trim(); }
    public String getMotDePasse() { return new String(passwordField.getPassword()).trim(); }
    public String getTypeUtilisateur() { return null; }
}
