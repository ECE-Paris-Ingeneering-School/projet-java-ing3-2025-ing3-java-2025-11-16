package org.chem.Controleur;

import org.chem.Dao.*;
import org.chem.Modele.*;
import org.chem.Vue.*;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Contrôleur pour la création d'un nouveau compte utilisateur.
 * Gère les actions de création et de retour à la page de connexion.
 */
public class CreationCompteControleur {

    /** La vue associée à ce contrôleur. */
    private CreationCompte vue;

    /**
     * Constructeur du contrôleur de création de compte.
     *
     * @param vue La vue de création de compte à contrôler.
     */
    public CreationCompteControleur(CreationCompte vue) {
        this.vue = vue;
        ajouterListeners();
    }

    /**
     * Ajoute les écouteurs d'événements (listeners) aux boutons de la vue.
     */
    private void ajouterListeners() {
        vue.getCreerBtn().addActionListener(this::creerCompte);
        vue.getBtnRetour().addActionListener(e -> {
            new ConnexionControleur(new Connexion(vue.getTypeUtilisateur()));
            vue.dispose();
        });
    }

    /**
     * Crée un compte utilisateur selon les informations saisies dans la vue.
     * Valide les champs, instancie le bon type d'utilisateur (Patient, Spécialiste ou Admin),
     * enregistre l'utilisateur en base de données via le DAO, puis redirige vers la connexion.
     *
     * @param e L'événement déclenché par l'appui sur le bouton "Créer".
     */
    private void creerCompte(ActionEvent e) {
        String nom = vue.getNomField().getText().trim();
        String prenom = vue.getPrenomField().getText().trim();
        String email = vue.getEmailField().getText().trim();
        String mdp = new String(vue.getMdpField().getPassword()).trim();

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || mdp.isEmpty()) {
            JOptionPane.showMessageDialog(vue, "Tous les champs sont obligatoires.");
            return;
        }

        DatabaseConnection db = DatabaseConnection.getDefaultInstance();
        UtilisateurDAO utilisateurDAO = db.getUtilisateurDAO();

        Utilisateur utilisateur = null;
        System.out.println("utilisateur = null");

        switch (vue.getTypeUtilisateur().toLowerCase()) {
            case "patient":
                // Par défaut, type de patient mis à 1 (modifiable si besoin)
                utilisateur = new Patient(nom, prenom, email, mdp, 1);
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
