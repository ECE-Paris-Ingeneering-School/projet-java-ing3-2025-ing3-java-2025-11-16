package org.chem.Controleur;
import org.chem.Modele.*;
import org.chem.Vue.*;

public class Main {
    public static void main(String[] args) {
        Utilisateur u = new Specialiste("Petit","Marc","marc.petit@gmail.com","pass123","J","d");
        //new Recherche(u);

        //new ChoixConnexion();

        Utilisateur a = new Admin("Petit","Marc","marc.petit@gmail.com","pass123");

        ChoixConnexion vue_choix = new ChoixConnexion();
        new ChoixConnexionControleur(vue_choix);

        //new AdminControleur(new AdminVue(a));
    }

}
