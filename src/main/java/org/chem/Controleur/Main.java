package org.chem.Controleur;
import org.chem.Modele.*;
import org.chem.Vue.*;


public class Main {
    public static void main(String[] args) {

        ChoixConnexion vue_choix = new ChoixConnexion();
        new ChoixConnexionControleur(vue_choix);

    }

}
