package org.chem.Controleur;
import org.chem.Modele.TypeUtilisateur;
import org.chem.Vue.ChoixConnexion;
import org.chem.Vue.Connexion;

public class ChoixConnexionControleur {
    private ChoixConnexion vue;
    private String typeUtilisateur;

    public ChoixConnexionControleur(ChoixConnexion vue) {
        this.vue = vue;
        ajouterListeners();
    }

    private void ajouterListeners() {
        vue.getPatientButton().addActionListener(e -> {
            typeUtilisateur = TypeUtilisateur.PATIENT.getCode();
            ouvrirConnexion();
        });

        vue.getSpecialisteButton().addActionListener(e -> {
            typeUtilisateur = TypeUtilisateur.SPECIALISTE.getCode();
            ouvrirConnexion();
        });

        vue.getAdminButton().addActionListener(e -> {
            typeUtilisateur = TypeUtilisateur.ADMIN.getCode();
            ouvrirConnexion();
        });
    }

    private void ouvrirConnexion() {
        Connexion vue_connexion = new Connexion(typeUtilisateur);
        new ConnexionControleur(vue_connexion);
        vue.dispose();
    }

    public String getTypeUtilisateur() {
        return typeUtilisateur;
    }
}
