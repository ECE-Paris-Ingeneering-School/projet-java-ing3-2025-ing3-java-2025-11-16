package org.chem.Controleur;

import org.chem.Dao.DatabaseConnection;
import org.chem.Dao.UtilisateurDAO;
import org.chem.Modele.*;
import org.chem.Vue.*;

import javax.swing.*;

public class ConnexionControleur {
    private Connexion vue;

    public ConnexionControleur(Connexion vue) {
        this.vue = vue;

        // Ajout des listeners ici
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

    private void seConnecter() {
        String email = vue.getEmail();
        String mdp = vue.getMotDePasse();

        if (email.isEmpty() || mdp.isEmpty()) {
            JOptionPane.showMessageDialog(vue, "Veuillez remplir tous les champs.");
            return;
        }

        DatabaseConnection db = DatabaseConnection.getDefaultInstance();
        UtilisateurDAO utilisateurDAO = db.getUtilisateurDAO();


        Utilisateur utilisateur = utilisateurDAO.seConnecter(email, mdp,vue.getTypeUtilisateur());

        if (utilisateur != null) {

            Session.setUtilisateur(utilisateur);

            JOptionPane.showMessageDialog(vue, "Bienvenue " + utilisateur.getPrenom() + " !");
            vue.dispose();


            switch (utilisateur) {
                case Patient patient :
                    new RechercheControleur(new Recherche());
                    break;
                case Specialiste specialiste :
                    new SpecialisteVue(utilisateur);
                    break;
                case Admin admin :
                    new AdminControleur(new AdminVue(utilisateur));
                    break;
                default :
                    JOptionPane.showMessageDialog(vue, "Type d'utilisateur non reconnu.");
            }

        } else {
            JOptionPane.showMessageDialog(vue, "Email ou mot de passe incorrect.");
        }
    }
}
