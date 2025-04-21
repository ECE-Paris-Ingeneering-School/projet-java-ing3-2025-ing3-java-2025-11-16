package org.chem.Vue;

import javax.swing.*;
import java.awt.*;

public class CreationCompte extends BaseFrame {
    private JTextField nomField, prenomField, emailField, SpecialiteField, LieuField;
    private JPasswordField mdpField;
    private JComboBox<String> typePatientBox; // visible si patient
    private String typeUtilisateur;
    private JButton btnRetour, creerBtn;

    public CreationCompte(String typeUtilisateur) {
        super();
        this.typeUtilisateur = typeUtilisateur;
        JPanel Contenu = getCenterPanel();
        JPanel boutonsPanel = new JPanel(new BorderLayout());

        JLabel titreLabel = new JLabel("Création de Compte "+typeUtilisateur, SwingConstants.CENTER);
        titreLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titreLabel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        Contenu.add(titreLabel, BorderLayout.NORTH);

        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        boutonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        nomField = new JTextField(10);
        prenomField = new JTextField(10);
        emailField = new JTextField(10);
        mdpField = new JPasswordField(10);
        SpecialiteField = new JTextField(10);
        LieuField = new JTextField(10);

        btnRetour = new JButton("Retour");
        panel.add(new JLabel("Nom :"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom :"));
        panel.add(prenomField);
        panel.add(new JLabel("Email :"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(mdpField);

        if (typeUtilisateur.equalsIgnoreCase("specialiste")) {
                panel.add(new JLabel("Specialité :"));
                panel.add(SpecialiteField);
                panel.add(new JLabel("Lieu :"));
                panel.add(LieuField);
                break;
        }

        creerBtn = new JButton("Créer le compte");
        Font bigFont = new Font("Arial", Font.BOLD, 20);
        Dimension bigSize = new Dimension(250, 60);

        creerBtn.setFont(bigFont);
        btnRetour.setFont(bigFont);

        creerBtn.setPreferredSize(bigSize);
        btnRetour.setPreferredSize(bigSize);

        boutonsPanel.add(btnRetour, BorderLayout.WEST);
        boutonsPanel.add(creerBtn, BorderLayout.EAST);
        Contenu.add(panel, BorderLayout.CENTER);
        Contenu.add(boutonsPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Getters pour le contrôleur
    public JTextField getNomField() { return nomField; }
    public JTextField getPrenomField() { return prenomField; }
    public JTextField getEmailField() { return emailField; }
    public JPasswordField getMdpField() { return mdpField; }
    public JTextField getSpecialiteField() { return SpecialiteField; }
    public JTextField getLieuField() { return LieuField; }
    public JComboBox<String> getTypePatientBox() { return typePatientBox; }
    public String getTypeUtilisateur() { return typeUtilisateur; }
    public JButton getBtnRetour() { return btnRetour; }
    public JButton getCreerBtn() { return creerBtn; }
}
