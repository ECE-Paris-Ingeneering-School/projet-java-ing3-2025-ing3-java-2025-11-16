package Modele;
import java.util.ArrayList;


public class Patient extends Utilisateur{
    private int typePatient;

    public Patient(int id, String nom, String prenom, String email, String mdp, int typePatient) {
        super(id, nom, prenom, email, mdp);
        this.typePatient = typePatient;

    }

    public int getTypePatient() { return typePatient; }
    public void setTypePatient(int typePatient) { this.typePatient = typePatient; }
}