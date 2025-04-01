package Vue;

import Modele.Specialiste;
import java.util.List;

public class SpecialisteVue {

    public void afficherSpecialise(Specialiste specialiste){
        System.out.println("Details du Spécialiste : ");
        System.out.println("ID: "+ specialiste.getIDspecialiste());
        System.out.println("Nom: "+ specialiste.getNom());
        System.out.println("Prenom: "+ specialiste.getPrenom());
        System.out.println("Specialisation: "+ specialiste.getSpecialisation());
        System.out.println("Lieux de consultation: "+ specialiste.getLieuxConsultation());
        System.out.println("Disponsibilités: "+ specialiste.getDisponibilites());
    }

    public void afficherListeSpecialistes(List<Specialiste> specialistes){
        System.out.println("Liste des specialistes : ");
        for(Specialiste specialiste : specialistes){
            System.out.println(specialistes);
        }
    }
}
