package Dao;

import Modele.Patient;
import java.util.List;

public interface PatientDAO {
    List<Patient> getAllPatients();

    Patient getPatientById(int id);

    void ajouterPatient(Patient patient);

    void supprimerPatient(int id);
}
