package Controleur;

import Modele.Utilisateur;
import java.util.ArrayList;

public class UtilisateurController {
    private ArrayList<Utilisateur> utilisateurs;

    public UtilisateurController() {
        this.utilisateurs = new ArrayList<>();
    }

    public void ajouterUtilisateur(Utilisateur u) {
        utilisateurs.add(u);
        System.out.println("Utilisateur ajouté avec succès : " + u.getNom());
    }

    public Utilisateur chercherUtilisateurParID(int id) {
        for (Utilisateur u : utilisateurs) {
            if (u.getId() == id)
            {
                return u;
            }
        }
        return null;
    }

    public boolean authentifierUtilisateur(int id, String mdp) {
        Utilisateur utilisateur = chercherUtilisateurParID(id);
        if (utilisateur != null && utilisateur.getMdp().equals(mdp)) {
            System.out.println("Authentification réussie pour : " + utilisateur.getNom());
            return true;
        } else {
            System.out.println("Échec de l'authentification. Vérifiez l'ID et le mot de passe.");
            return false;
        }
    }



    public void affichertousUtilisateurs() {
        System.out.println("Liste des utilisateurs enregistrés :");
        for (Utilisateur u : utilisateurs) {
            u.afficherUtilisateur();
        }
    }
}

