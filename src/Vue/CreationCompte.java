package Vue;

import Modele.*;
import Dao.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class CreationCompte extends BaseFrame {
    private JTextField nomField, prenomField, emailField,SpecialiteField,LieuField;
    private JPasswordField mdpField;
    private JComboBox<String> typePatientBox; // visible si patient
    private String typeUtilisateur;

    public CreationCompte(String typeUtilisateur) {
        super();
        this.typeUtilisateur = typeUtilisateur;
        JPanel Contenu = getCenterPanel();
        JPanel boutonsPanel = new JPanel(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        boutonsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        nomField = new JTextField(10);
        prenomField = new JTextField(10);
        emailField = new JTextField(10);
        mdpField = new JPasswordField(10);
        SpecialiteField = new JTextField(10);
        LieuField = new JTextField(10);

        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            new ChoixConnexion(typeUtilisateur);
            dispose();
        });

        panel.add(new JLabel("Nom :"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom :"));
        panel.add(prenomField);
        panel.add(new JLabel("Email :"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(mdpField);

        switch (typeUtilisateur) {
            case "patient":
                typePatientBox = new JComboBox<>(new String[]{"1 - Nouveau", "2 - Ancien"});
                typePatientBox.setVisible(true);
                panel.add(typePatientBox);
                break;

            case "specialiste":
                panel.add(new JLabel("Specialité :"));
                panel.add(SpecialiteField);
                panel.add(new JLabel("Lieu :"));
                panel.add(LieuField);
                break;
        }

        JButton creerBtn = new JButton("Créer le compte");
        Font bigFont = new Font("Arial", Font.BOLD, 20);
        Dimension bigSize = new Dimension(250, 60);

        creerBtn.setFont(bigFont);
        btnRetour.setFont(bigFont);

        creerBtn.setPreferredSize(bigSize);
        btnRetour.setPreferredSize(bigSize);

        creerBtn.addActionListener(this::creerCompte);

        boutonsPanel.add(btnRetour, BorderLayout.WEST);
        boutonsPanel.add(creerBtn, BorderLayout.EAST);
        Contenu.add(panel, BorderLayout.CENTER);

        Contenu.add(boutonsPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void creerCompte(ActionEvent e) {
        String nom = nomField.getText().trim();
        String prenom = prenomField.getText().trim();
        String email = emailField.getText().trim();
        String mdp = new String(mdpField.getPassword()).trim();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.");
            return;
        }

        DatabaseConnection db = DatabaseConnection.getInstance("rdv_specialiste", "root", "root");
        UtilisateurDAO utilisateurDAO = db.getUtilisateurDAO();

        Utilisateur utilisateur = null;

        switch (typeUtilisateur) {
            case "patient":
                int type = typePatientBox.getSelectedIndex() + 1;
                utilisateur = new Patient(nom, prenom, email, mdp, type);
                break;
            case "specialiste":
                String specialite = SpecialiteField.getText().trim();
                String etablissement = LieuField.getText().trim();
                utilisateur = new Specialiste(nom, prenom, email, mdp, specialite, etablissement);
                break;
            case "Admin":
                utilisateur = new Admin(nom, prenom, email, mdp);
                break;
        }

        if (utilisateur != null) {
            try {
                utilisateurDAO.ajouter(utilisateur);
                JOptionPane.showMessageDialog(this, "Compte créé avec succès !");
                new Connexion(typeUtilisateur);
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de la création.");
                ex.printStackTrace();
            }

        }
    }
}
