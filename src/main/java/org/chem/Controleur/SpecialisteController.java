package org.chem.Controleur;

import org.chem.Modele.Specialiste;
import java.util.ArrayList;

public class SpecialisteController {
    private ArrayList<Specialiste> specialistes;

    public SpecialisteController() {
        this.specialistes = new ArrayList<>();
    }

    public boolean ajouterSpecialiste(Specialiste s) {
        if (s != null) {
            specialistes.add(s);
            System.out.println("Spécialiste ajouté : " + s.getNom());
            return true;
        }
        return false;
    }

    public ArrayList<Specialiste> getSpecialistes() {
        return specialistes;
    }
}
