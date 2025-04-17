package Controleur;
import Dao.*;
import Modele.*;
import Vue.*;

import java.sql.Time;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        Utilisateur u2 = new Specialiste("Martin", "Sophie", "sophie.martin@gmail.com", "mdp123", "Cardiologue", "Paris");

        //TestApplication.lancerMenu();
        Utilisateur u = new Patient("Petit","Marc","marc.petit@gmail.com","pass123",1);
        new SpecialisteVue(u2);

        //new Recherche(u);
        //new PageAccueil();
    }

}
