package org.chem;

import org.chem.Controleur.ChoixConnexionControleur;
import org.chem.Vue.ChoixConnexion;

public class App {
    public static void main(String[] args) {

        //new ChoixConnexion();
        ChoixConnexion vue_choix = new ChoixConnexion();
        new ChoixConnexionControleur(vue_choix);
    }
}

