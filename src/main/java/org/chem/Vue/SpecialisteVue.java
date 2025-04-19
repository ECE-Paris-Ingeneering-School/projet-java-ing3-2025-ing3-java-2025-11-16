package org.chem.Vue;

import org.chem.Modele.Specialiste;
import org.chem.Modele.Utilisateur;
import org.chem.Controleur.SpecialisteController;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SpecialisteVue extends BaseFrame {
    // Contrôleur pour interagir avec la couche métier
    private final SpecialisteController controller;
    // Zone de texte centrale pour l'affichage
    private final JTextArea affichage;

    public SpecialisteVue(Utilisateur utilisateur) {
        super(utilisateur);
        this.controller = new SpecialisteController();

        setTitle("Gestion des Spécialistes");
        setMinimumSize(new Dimension(1000, 700));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // --- ZONE CENTRALE ---
        affichage = new JTextArea();
        affichage.setEditable(false);
        affichage.setFont(new Font("Monospaced", Font.PLAIN, 14));
        affichage.setBackground(new Color(248, 248, 248));
        affichage.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(affichage);
        scrollPane.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        add(scrollPane, BorderLayout.CENTER);

        // --- PIED DE PAGE AVEC BOUTONS ---
        JPanel footer = new JPanel();
        footer.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 15));
        footer.setBackground(Color.WHITE);

        JButton btnLister = new JButton("📄 Lister les spécialistes");
        JButton btnAjouter = new JButton("➕ Ajouter un spécialiste");
        // Uniformisation des tailles et polices des boutons
        Dimension btnSize = new Dimension(240, 40);
        btnLister.setPreferredSize(btnSize);
        btnAjouter.setPreferredSize(btnSize);
        // Actions des bouttons
        btnLister.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        btnAjouter.setFont(new Font("Segoe UI", Font.PLAIN, 15));

        footer.add(btnLister);
        footer.add(btnAjouter);
        add(footer, BorderLayout.SOUTH);

        // --- ACTIONS ---
        btnLister.addActionListener(e -> listerSpecialistes());
        btnAjouter.addActionListener(e -> ajouterSpecialiste());

        pack(); // ajuste au contenu
        setVisible(true);
    }

    private void listerSpecialistes() {
        affichage.setText("");
        ArrayList<Specialiste> liste = controller.getSpecialistes();
        if (liste.isEmpty()) {
            affichage.append("⚠ Aucun spécialiste trouvé.\n");
        } else {
            for (Specialiste s : liste) {
                affichage.append("🔹 " + s + "\n");
            }
        }
    }

    private void ajouterSpecialiste() {
        try {
            // Saisie utilisateur via JOptionPane
            String nom = JOptionPane.showInputDialog(this, "Nom du spécialiste :");
            String prenom = JOptionPane.showInputDialog(this, "Prénom :");
            String email = JOptionPane.showInputDialog(this, "Email :");
            String mdp = JOptionPane.showInputDialog(this, "Mot de passe :");
            String specialisation = JOptionPane.showInputDialog(this, "Spécialisation :");
            String lieu = JOptionPane.showInputDialog(this, "Lieu d'exercice :");
            // Vérification que toutes les saisies sont valides
            if (nom != null && prenom != null && email != null && mdp != null && specialisation != null && lieu != null) {
                Specialiste s = new Specialiste(Utilisateur.ID_NEW_USER,nom, prenom, email, mdp, specialisation, lieu);
                boolean success = controller.ajouterSpecialiste(s);
                if (success) {
                    JOptionPane.showMessageDialog(this, "✅ Spécialiste ajouté avec succès !");
                    listerSpecialistes();
                } else {
                    JOptionPane.showMessageDialog(this, "❌ Erreur lors de l'ajout.");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erreur : " + e.getMessage());
        }
    }
}
