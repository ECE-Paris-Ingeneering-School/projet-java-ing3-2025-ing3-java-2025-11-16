package org.chem.Vue;

import javax.swing.*;
import java.awt.*;

public class Connexion extends BaseFrame {

    private JTextField emailField;
    private JPasswordField passwordField;
    private String typeUtilisateur;

    private JButton btnRetour;
    private JButton loginBtn;
    private JButton signupBtn;

    public Connexion(String typeUtilisateur) {
        super();
        this.typeUtilisateur = typeUtilisateur;
        initConnection();
    }

    public Connexion() {
        this("Utilisateur");
    }

    /**
     *
     * @param login Le login de l'utilisateur.
     * @param password Le mot de passe de l'utilisateur.
     */
    public Connexion(String login, String password) {
        this("Utilisateur");
        setLogin(login);
        setPassword(password);
    }

    public void setPassword(String password) {
        passwordField.setText(password);
    }

    public void setLogin(String login) {
        emailField.setText(login);
    }

    private void initConnection() {
        JPanel contenu = getCenterPanel();
        contenu.setLayout(new GridBagLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        formPanel.setMaximumSize(new Dimension(800, 500));

        JLabel titreLabel = new JLabel("Connexion", SwingConstants.CENTER);
        titreLabel.setFont(new Font("Arial", Font.BOLD, 40));
        titreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titreLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        formPanel.add(titreLabel);

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

        JPanel passwordPanel = new JPanel(new BorderLayout());
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.setMaximumSize(new Dimension(500, 50));
        passwordPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel passwordLabel = new JLabel("Mot de passe :");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 15));
        passwordLabel.setHorizontalAlignment(SwingConstants.LEFT);
        passwordPanel.add(passwordLabel, BorderLayout.NORTH);

        passwordField = new JPasswordField(40);
        passwordField.setMaximumSize(new Dimension(500, 50));
        passwordField.setFont(new Font("Arial", Font.PLAIN, 16));
        passwordPanel.add(passwordField, BorderLayout.CENTER);

        formPanel.add(passwordPanel);
        formPanel.add(Box.createVerticalStrut(25));

        loginBtn = new JButton("Se connecter");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 16));
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setPreferredSize(new Dimension(160, 45));
        loginBtn.setMaximumSize(new Dimension(200, 45));
        loginBtn.setBackground(Color.decode("#649FCB"));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createLineBorder(Color.decode("#649FCB")));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(loginBtn);

        formPanel.add(Box.createVerticalStrut(20));

        signupBtn = new JButton("Pas de compte ?");
        signupBtn.setFont(new Font("Arial", Font.PLAIN, 15));
        signupBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupBtn.setContentAreaFilled(false);
        signupBtn.setBorderPainted(false);
        signupBtn.setFocusPainted(false);
        signupBtn.setForeground(Color.BLACK);
        signupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        formPanel.add(signupBtn);

        btnRetour = new JButton("Retour");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        contenu.add(formPanel, gbc);

        setVisible(true);
    }

    // GETTERS pour le contrôleur
    public JButton getLoginBtn() { return loginBtn; }
    public JButton getSignupBtn() { return signupBtn; }
    public JButton getBtnRetour() {return btnRetour;}

    public String getEmail() { return emailField.getText().trim(); }
    public String getMotDePasse() { return new String(passwordField.getPassword()).trim(); }
    public String getTypeUtilisateur() { return typeUtilisateur; }

}
