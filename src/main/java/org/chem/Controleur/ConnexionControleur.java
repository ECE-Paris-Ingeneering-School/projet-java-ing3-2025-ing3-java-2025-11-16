package org.chem.Controleur;

import org.chem.Dao.DatabaseConnection;
import org.chem.Dao.UtilisateurDAO;
import org.chem.Modele.*;
import org.chem.Vue.*;

import javax.swing.*;

/**
 * Contrôleur pour la vue de connexion.
 * Gère les actions de connexion, d'inscription et de retour au choix de connexion.
 */
public class ConnexionControleur {

    /** La vue associée à ce contrôleur. */
    private Connexion vue;

    /**
     * Constructeur du contrôleur de connexion.
     *
     * @param vue La vue de connexion à contrôler.
     */
    public ConnexionControleur(Connexion vue) {
        this.vue = vue;

        // Ajout des listeners aux boutons
        vue.getLoginBtn().addActionListener(e -> seConnecter());

        vue.getSignupBtn().addActionListener(e -> {
            new CreationCompteControleur(new CreationCompte(vue.getTypeUtilisateur()));
            vue.dispose();
        });

        vue.getBtnRetour().addActionListener(e -> {
            new ChoixConnexionControleur(new ChoixConnexion());
            vue.dispose();
        });
    }

    /**
     * Tente de connecter l'utilisateur en vérifiant ses identifiants.
     * Si la connexion est réussie, ouvre la fenêtre appropriée selon le type d'utilisateur.
     * Sinon, affiche un message d'erreur.
     */
    private void seConnecter() {
        String email = vue.getEmail();
        String motDePasse = vue.getMotDePasse();

        if (email.isEmpty() || motDePasse.isEmpty()) {
            JOptionPane.showMessageDialog(vue, "Veuillez remplir tous les champs.");
            return;
        }

        DatabaseConnection db = DatabaseConnection.getDefaultInstance();
        UtilisateurDAO utilisateurDAO = db.getUtilisateurDAO();

        Utilisateur utilisateur = utilisateurDAO.seConnecter(email, motDePasse, vue.getTypeUtilisateur());

        if (utilisateur != null) {
            Session.setUtilisateur(utilisateur);

            JOptionPane.showMessageDialog(vue, "Bienvenue " + utilisateur.getPrenom() + " !");
            vue.dispose();

            // Ouvre la vue correspondant au type d'utilisateur
            switch (utilisateur) {
                case Patient patient -> {
                    new RechercheControleur(new Recherche());
                }
                case Specialiste specialiste -> new SpecialisteVue(utilisateur);
                case Admin admin -> new AdminControleur(new AdminVue(utilisateur));
                default -> JOptionPane.showMessageDialog(vue, "Type d'utilisateur non reconnu.");
            }
        } else {
            JOptionPane.showMessageDialog(vue, "Email ou mot de passe incorrect.");
        }
    }
}
