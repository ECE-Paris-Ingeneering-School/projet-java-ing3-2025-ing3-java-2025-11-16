package Modele;
import java.util.ArrayList;
import java.util.HashMap;

    public class Statistiques {
        private ArrayList<Patient> patients;
        private ArrayList<Specialiste> specialistes;
        private ArrayList<RendezVous> rendezVous;

        public Statistiques(ArrayList<Patient> patients, ArrayList<Specialiste> specialistes, ArrayList<RendezVous> rendezVous) {
            this.patients = patients;
            this.specialistes = specialistes;
            this.rendezVous = rendezVous;
        }

        public int calculerNombreTotalPatients() {
            return patients.size();
        }

        public int calculerNombreTotalRendezVous() {
            return rendezVous.size();
        }

        public String calculerSpecialisationLaPlusDemandée() {
            HashMap<String, Integer> specialisationCount = new HashMap<>();
            for (RendezVous rdv : rendezVous) {
                String specialisation = rdv.getSpecialiste().getSpecialisation();
                specialisationCount.put(specialisation, specialisationCount.getOrDefault(specialisation, 0) + 1);
            }

            String specialisationMax = "";
            int maxCount = 0;
            for (String specialisation : specialisationCount.keySet()) {
                if (specialisationCount.get(specialisation) > maxCount) {
                    maxCount = specialisationCount.get(specialisation);
                    specialisationMax = specialisation;
                }
            }

            return specialisationMax;
        }

        public double calculerMoyenneRendezVousParPatient() {
            if (patients.size() == 0) return 0;
            return (double) rendezVous.size() / patients.size();
        }
    }




