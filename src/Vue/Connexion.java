package Vue;

import Modele.*;
import Dao.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Connexion extends BaseFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private String typeUtilisateur;

    public Connexion(String typeUtilisateur) {
        super();
        this.typeUtilisateur = typeUtilisateur;
        JPanel mainPanel = getMainPanel();

        JPanel panel = new JPanel(new GridLayout(4, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            new ChoixConnexion(typeUtilisateur);
            dispose();
        });

        emailField = new JTextField();
        passwordField = new JPasswordField();

        panel.add(new JLabel("Email :"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe :"));
        panel.add(passwordField);

        JButton loginBtn = new JButton("Se connecter");
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seConnecter();
            }
        });

        panel.add(btnRetour);

        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(loginBtn, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void seConnecter() {
        String email = emailField.getText().trim();
        String mdp = new String(passwordField.getPassword()).trim();

        if (email.isEmpty() || mdp.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez remplir tous les champs.");
            return;
        }

        DatabaseConnection db = DatabaseConnection.getInstance("rdv_specialiste", "root", "root");
        UtilisateurDAO utilisateurDAO = db.getUtilisateurDAO();

        Utilisateur utilisateur = utilisateurDAO.seConnecter(email, mdp, typeUtilisateur);

        if (utilisateur != null) {
            JOptionPane.showMessageDialog(this, "Bienvenue " + utilisateur.getPrenom() + " !");

            if (utilisateur instanceof Patient) {
                new PatientVue(utilisateur);
            } else if (utilisateur instanceof Specialiste) {
                new SpecialisteVue(utilisateur);
            } else {
                JOptionPane.showMessageDialog(this, "Type d'utilisateur non reconnu.");
                return;
            }

            dispose(); // Ferme la fenêtre de connexion

        } else {
            JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect.");
        }
    }

}