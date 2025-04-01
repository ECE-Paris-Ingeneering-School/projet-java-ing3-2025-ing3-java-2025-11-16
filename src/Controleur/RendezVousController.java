package Controleur;

import Modele.Patient;
import java.util.ArrayList;

public class RendezVousController {
    /*private ArrayList<Patient> patients;

    public RendezVousController() {
        this.patients = new ArrayList<>();
    }

    public void ajouterPatient(Patient p) {
        patients.add(p);
    }

    public void ajouterRendezVous(int IDpatient, String rdv) {
        Patient patient = chercherPatientParID(IDpatient);
        if (patient != null) {
            patient.setIDpatient(rdv); // Ajouter le rendez-vous à l'historique
        } else {
            System.out.println("Patient avec ID " + IDpatient + " non trouvé.");
        }
    }

    public Patient chercherPatientParID(int id) {
        for (Patient p : patients) {
            if (p.getIDpatient() == id) {
                return p;
            }
        }
        return null;
    }

    public void afficherRendezVous(int IDpatient) {
        Patient patient = chercherPatientParID(IDpatient);
        if (patient != null) {
            System.out.println("Rendez-vous de " + patient.getNom() + " " + patient.getPrenom() + " : ");
            ArrayList<String> historiqueRDV = patient.getHistoriqueRDV();
            for (String rdv : historiqueRDV) {
                System.out.println(rdv);
            }
        } else {
            System.out.println("Patient avec ID " + IDpatient + " non trouvé.");
        }
    }*/
}

