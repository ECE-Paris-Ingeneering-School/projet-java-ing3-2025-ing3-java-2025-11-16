package org.chem.Modele;

public class Patient extends Utilisateur {
    private int type;


    public Patient(int id, String nom, String prenom, String email, String mdp, int typePatient) {
        super(id, nom, prenom, email, mdp, TypeUtilisateur.PATIENT);
        this.type = typePatient;
    }

    public int getType() { return type; }
    public void setType(int typePatient) { this.type = typePatient; }

    @Override
    public String toString() {
        return super.toString() + ", TypePatient : " + type;
    }
}
