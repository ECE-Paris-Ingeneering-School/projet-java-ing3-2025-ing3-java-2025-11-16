package Vue;

import Modele.*;
import Dao.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Connexion extends BaseFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private String typeUtilisateur;

    public Connexion(String typeUtilisateur) {
        super();
        this.typeUtilisateur = typeUtilisateur;
        JPanel contenu = getCenterPanel();
        JPanel boutonPanel = new JPanel(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(8, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        boutonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton btnRetour = new JButton("Retour");
        btnRetour.addActionListener(e -> {
            new ChoixConnexion(typeUtilisateur);
            dispose();
        });

        emailField = new JTextField(10);
        passwordField = new JPasswordField(10);

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

        Font bigFont = new Font("Arial", Font.BOLD, 20);
        Dimension bigSize = new Dimension(250, 60);

        loginBtn.setFont(bigFont);
        btnRetour.setFont(bigFont);

        loginBtn.setPreferredSize(bigSize);
        btnRetour.setPreferredSize(bigSize);

        boutonPanel.add(loginBtn, BorderLayout.EAST);
        boutonPanel.add(btnRetour, BorderLayout.WEST);

        contenu.add(boutonPanel, BorderLayout.SOUTH);
        contenu.add(panel, BorderLayout.CENTER);

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
            System.out.println("Type réel de l'utilisateur : " + utilisateur.getClass());

            if (utilisateur instanceof Patient) {
                System.out.println("avant");
                new Recherche(utilisateur);
                System.out.println("Paatient");
            } else if (utilisateur instanceof Specialiste) {
                new SpecialisteVue(utilisateur);
            } else if (utilisateur instanceof Admin) {
                new AdminVue(utilisateur);
            } else {
                System.out.println("??");

                JOptionPane.showMessageDialog(this, "Type d'utilisateur non reconnu.");
                System.out.println("???");
                return;
            }

            System.out.println("Bonjour");

            dispose(); // Ferme la fenêtre de connexion

        } else {
            JOptionPane.showMessageDialog(this, "Email ou mot de passe incorrect.");
        }
    }

}