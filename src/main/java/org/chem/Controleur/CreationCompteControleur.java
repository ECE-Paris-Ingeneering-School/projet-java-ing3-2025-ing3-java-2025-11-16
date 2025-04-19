package org.chem.Controleur;

import org.chem.Dao.DatabaseConnection;
import org.chem.Dao.UtilisateurDAO;
import org.chem.Modele.Admin;
import org.chem.Modele.Patient;
import org.chem.Modele.Specialiste;
import org.chem.Modele.Utilisateur;
import org.chem.Vue.Connexion;
import org.chem.Vue.CreationCompte;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class CreationCompteControleur {
    private CreationCompte vue;

    public CreationCompteControleur(CreationCompte vue) {
        this.vue = vue;
        ajouterListeners();
    }

    private void ajouterListeners() {
        vue.getCreerBtn().addActionListener(this::creerCompte);
        vue.getBtnRetour().addActionListener(e -> {
            Connexion vueConnexion = new Connexion(vue.getTypeUtilisateur());
            new ConnexionControleur(vueConnexion);
            vue.dispose();
        });
    }

    private void creerCompte(ActionEvent e) {
        String nom = vue.getNomField().getText().trim();
        String prenom = vue.getPrenomField().getText().trim();
        String email = vue.getEmailField().getText().trim();
        String mdp = new String(vue.getMdpField().getPassword()).trim();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty()) {
            JOptionPane.showMessageDialog(vue, "Tous les champs sont obligatoires.");
            return;
        }

        DatabaseConnection db = DatabaseConnection.getInstance();
        UtilisateurDAO utilisateurDAO = db.getUtilisateurDAO();

        Utilisateur utilisateur = null;

        switch (vue.getTypeUtilisateur()) {
            case "patient":
                int type = vue.getTypePatientBox().getSelectedIndex() + 1;
                utilisateur = new Patient(nom, prenom, email, mdp, type);
                break;
            case "specialiste":
                String specialite = vue.getSpecialiteField().getText().trim();
                String etablissement = vue.getLieuField().getText().trim();
                utilisateur = new Specialiste(nom, prenom, email, mdp, specialite, etablissement);
                break;
            case "admin":
                utilisateur = new Admin(nom, prenom, email, mdp);
                break;
        }

        if (utilisateur != null) {
            try {
                utilisateurDAO.ajouter(utilisateur);
                JOptionPane.showMessageDialog(vue, "Compte créé avec succès !");
                new ConnexionControleur(new Connexion(vue.getTypeUtilisateur()));
                vue.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vue, "Erreur lors de la création.");
                ex.printStackTrace();
            }
        }
    }
}
