package org.chem.Controleur;

import org.chem.Vue.ChoixConnexion;
import org.chem.Vue.Connexion;

/**
 * Contrôleur pour la vue de choix de type de connexion (Patient, Spécialiste, Admin).
 * Gère les interactions de l'utilisateur pour sélectionner son type et ouvrir la fenêtre de connexion correspondante.
 */
public class ChoixConnexionControleur {

    /** La vue associée à ce contrôleur. */
    private ChoixConnexion vue;

    /** Le type d'utilisateur sélectionné (Patient, Specialiste, Admin). */
    private String typeUtilisateur;

    /**
     * Constructeur du contrôleur.
     *
     * @param vue La vue de choix de connexion à contrôler.
     */
    public ChoixConnexionControleur(ChoixConnexion vue) {
        this.vue = vue;
        ajouterListeners();
    }

    /**
     * Ajoute les listeners aux boutons pour gérer la sélection du type d'utilisateur.
     */
    private void ajouterListeners() {
        vue.getPatientButton().addActionListener(e -> {
            typeUtilisateur = "Patient";
            ouvrirConnexion();
        });

        vue.getSpecialisteButton().addActionListener(e -> {
            typeUtilisateur = "Specialiste";
            ouvrirConnexion();
        });

        vue.getAdminButton().addActionListener(e -> {
            typeUtilisateur = "Admin";
            ouvrirConnexion();
        });
    }

    /**
     * Ouvre la fenêtre de connexion correspondant au type d'utilisateur sélectionné,
     * puis ferme la fenêtre de choix de connexion actuelle.
     */
    private void ouvrirConnexion() {
        Connexion vue_connexion = new Connexion(typeUtilisateur);
        new ConnexionControleur(vue_connexion);
        vue.dispose();
    }

    /**
     * Récupère le type d'utilisateur sélectionné.
     *
     * @return Le type d'utilisateur (Patient, Specialiste, Admin).
     */
    public String getTypeUtilisateur() {
        return typeUtilisateur;
    }
}
