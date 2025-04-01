package Vue;

import Modele.Patient;
import java.util.List;

public class PatientVue {

    public void afficherPatient(Patient patient){
        System.out.println("Details du Patient : ");
        System.out.println("ID: " + patient.getIDpatient());
        System.out.println("Nom: " + patient.getNom());
        System.out.println("Prenom: " + patient.getPrenom());
        System.out.println("Type de Patient : " + patient.getTypePatient());
        System.out.println("Date d'inscription: " + patient.getInscriptionDate());
        System.out.println("Historique des Rendez-Vous: " + patient.getHistoriqueRDV());
    }

    public void affihcerListePatients(List<Patient> patients){
        System.out.println("Liste des Patients: ");
        for(Patient patient : patients){
            System.out.println("ID : " + patient.getIDpatient() +
                    ", Nom : " + patient.getNom() +
                    ", Prenom: " + patient.getPrenom() +
                    ", Type: " + patient.getTypePatient());
        }

    }
}
