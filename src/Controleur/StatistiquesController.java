package Controleur;

import Modele.Patient;
import java.util.ArrayList;

    public class StatistiquesController {
        /*private ArrayList<Patient> patients;

        public StatistiquesController() {
            this.patients = new ArrayList<>();
        }

        public void ajouterPatient(Patient p) {
            patients.add(p);
        }

        public void afficherStatistiques() {
            int totalPatients = patients.size();
            System.out.println("Nombre total de patients : " + totalPatients);

            int countVIP = 0;
            int countRegular = 0;

            for (Patient p : patients) {
                if (p.getTypePatient().equalsIgnoreCase("VIP")) {
                    countVIP++;
                } else if (p.getTypePatient().equalsIgnoreCase("Regular")) {
                    countRegular++;
                }
            }

            System.out.println("Nombre de patients VIP : " + countVIP);
            System.out.println("Nombre de patients réguliers : " + countRegular);

            int totalRendezVous = 0;
            for (Patient p : patients) {
                totalRendezVous += p.getHistoriqueRDV().size();
            }

            System.out.println("Nombre total de rendez-vous : " + totalRendezVous);

            for (Patient p : patients) {
                System.out.println("\nStatistiques pour le patient " + p.getNom() + " " + p.getPrenom() + ":");
                System.out.println("Rendez-vous passés : " + p.getHistoriqueRDV().size());
                System.out.println("Date d'inscription : " + p.getInscriptionDate());
            }
        }


        public void afficherStatistiquesPatient(int IDpatient) {
            Patient patient = chercherPatientParID(IDpatient);
            if (patient != null) {
                System.out.println("\nStatistiques pour " + patient.getNom() + " " + patient.getPrenom() + ":");
                System.out.println("Rendez-vous passés : " + patient.getHistoriqueRDV().size());
                System.out.println("Date d'inscription : " + patient.getInscriptionDate());
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
        }*/
    }

