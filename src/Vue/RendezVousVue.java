package Vue;

import Modele.RendezVous;
import java.util.List;

public class RendezVousVue {

    public void afficherRendezVous(RendezVous rdv){
        System.out.println("Informations RendezVous : ");
        System.out.println("ID : " + rdv.getIDrdv());
        System.out.println("Patient: " + rdv.getPatient());
        System.out.println("Spécialiste: " + rdv.getSpecialiste());
        System.out.println("Date et Heure: " + rdv.dateHeure());
        System.out.println("Statut: " + rdv.statut());
    }

    public void afficherListeRendezVous(List<RendezVous> rdvs){
        System.out.println("Liste des Rendez-Vous :"){
            for(RendezVous rdv : rdvs){
                System.out.println(rdv);
            }
        }
    }
}
