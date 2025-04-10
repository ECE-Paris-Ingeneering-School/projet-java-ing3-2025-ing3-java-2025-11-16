package Modele;

public class Patient extends Utilisateur {
    private int type;

    // Constructeur sans ID (utilisé avant l'insertion en base)
    public Patient(String nom, String prenom, String email, String mdp, int typePatient) {
        super(nom, prenom, email, mdp);
        this.type = typePatient;
    }

    // Constructeur avec ID (utilisé après récupération depuis la base)
    public Patient(int id, String nom, String prenom, String email, String mdp, int typePatient) {
        super(id, nom, prenom, email, mdp);
        this.type = typePatient;
    }

    public int getType() { return type; }
    public void setType(int typePatient) { this.type = typePatient; }

    @Override
    public String toString() {
        return super.toString() + ", TypePatient : " + type;
    }
}
