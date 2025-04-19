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

import static org.chem.Modele.Utilisateur.ID_NEW_USER;

public class CreationCompteControleur {
    private CreationCompte vue;

    public CreationCompteControleur(CreationCompte vue) {
        this.vue = vue;
        ajouterListeners();
    }

    private void ajouterListeners() {
        vue.getCreerBtn().addActionListener(this::creerCompte);
        vue.getBtnRetour().addActionListener(e -> {
            Connexion vueConnexion = new Connexion(vue.getTypeUtilisateurCode());
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
            case PATIENT:
                int type = vue.getTypePatientBox().getSelectedIndex() + 1;
                utilisateur = new Patient(ID_NEW_USER, nom, prenom, email, mdp, type);
                break;
            case SPECIALISTE:
                String specialite = vue.getSpecialiteField().getText().trim();
                String etablissement = vue.getLieuField().getText().trim();
                utilisateur = new Specialiste(ID_NEW_USER,  nom, prenom, email, mdp, specialite, etablissement);
                break;
            case ADMIN:
                utilisateur = new Admin(ID_NEW_USER, nom, prenom, email, mdp);
                break;
        }

        if (utilisateur != null) {
            try {
                utilisateurDAO.ajouter(utilisateur);
                JOptionPane.showMessageDialog(vue, "Compte créé avec succès !");
                new ConnexionControleur(new Connexion(vue.getTypeUtilisateurCode()));
                vue.dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vue, "Erreur lors de la création.");
                ex.printStackTrace();
            }
        }
    }
}
