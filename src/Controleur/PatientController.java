package Controleur;

import Modele.Patient;
import java.util.ArrayList;

    public class PatientController {
        private ArrayList<Patient> patients;

        public PatientController() {
            this.patients = new ArrayList<>();
        }

        public void ajouterPatient(Patient p) {
            patients.add(p);
        }

        public void afficherPatients() {
            for (Patient p : patients) {
                System.out.println(p);
            }
        }
    }


